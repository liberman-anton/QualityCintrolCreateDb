package tel_ran.quality.controller;

import java.time.LocalDate;
import java.util.*;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import tel_ran.quality.model.dao.QualityOrm;
import tel_ran.quality.model.entities.*;

public class QualityCreateDb {

	private static final int N_COMPANY = 2;
	private static final int N_SERVICES = 5;
	private static final int MAX_N_CLIENTS_OF_SERVICE = 2;
	private static final int MAX_N_QUESTIONS = 10;
	private static final int MAX_THRESHOLD = 50;
	private static final int MIN_THRESHOLD = 20;
	private static final int MAX_GRADE = 10;
	private static final int MIN_GRADE_POSITIVE = 4;
	private static final int PERIOD = 30;
	private static final int MAX_N_FEEDBACKS = 2;
	private static final int MIN_N_TO_OPEN_TICKET = 10;
	private static final int N_DAYS = 200;
	private static final int START_YEAR = 2016;
	private static final int START_MONTH = 1;
	
	static QualityOrm qualityOrm;
	static Random gen = new Random();
	static int idEmployee = 1000000;
	static int idQuestion = 100;
	static int idClient = 1000000;
	static LocalDate startDate = LocalDate.of(START_YEAR, START_MONTH, 1);
	
	public static void main(String[] args) {
		try (AbstractApplicationContext ctx = new FileSystemXmlApplicationContext("beans.xml")) {
			qualityOrm = ctx.getBean(QualityOrm.class);

			createRandomQuestion(0);
			for (int c = 1; c <= N_COMPANY; c++) {
				Set<String> serviceNames = new HashSet<>();
				for (int i = 1 + c * 10; i <= N_SERVICES + c * 10; i++) {
					createStructureOfService(serviceNames, i);
					createActionsOfClientsAndEmployees(i);
				}
				createRandomCompany(c, serviceNames);
			}
		}
	}

	private static void createActionsOfClientsAndEmployees(int i) {
		for (int day = 1; day < N_DAYS; day++) {
			LocalDate date = createDate(day); 
			for (int j = 0; j < gen.nextInt(MAX_N_CLIENTS_OF_SERVICE); j++) {
				Set<Integer> setFeedbackIds = new HashSet<>();
				for (int f = 0; f < 1 + gen.nextInt(MAX_N_FEEDBACKS); f++) {
					Integer feedbackId = createRandomFeedback("service #" + i,date);
					setFeedbackIds.add(feedbackId);
				}
				CreateRandomClient(setFeedbackIds);
			}
			List<Ticket> tickets = qualityOrm.getListOfTickets();
			if(tickets != null && !tickets.isEmpty()){
				for (Ticket ticket : tickets) {
					createActionsOfEmployees(ticket, date);
					createActionsOfManagers(ticket, date);
				}
			}
		}
	}

	private static void createActionsOfManagers(Ticket ticket, LocalDate date) {
		if(ticket.getStatus().equals("inProgress"))
			if(gen.nextInt(10) == 0)
				qualityOrm.setClose(ticket.getId(), date);
	}

	private static void createActionsOfEmployees(Ticket ticket, LocalDate date) {
		if(ticket.getStatus().equals("open"))
			if(gen.nextInt(5) == 0)
				qualityOrm.setInProgress(ticket.getId(), date);
	}

	private static void createStructureOfService(Set<String> serviceNames, int i) {
		Integer idTriggerQueForAll = createRandomTrigger("question" + 0);
		HashSet<Integer> idTriggers = new HashSet<>();
		idTriggers.add(idTriggerQueForAll);
		for (int q = 1; q < 2 + gen.nextInt(MAX_N_QUESTIONS); q++) {
			createRandomQuestion(++idQuestion);
			idTriggers.add(createRandomTrigger("question" + idQuestion));
		}
		createRandomService(i, createRandomEmployee(), idTriggers);
		serviceNames.add("service #" + i);
	}

	private static void createRandomCompany(int i, Set<String> serviceNames) {
		qualityOrm.addCompany("Company #" + i, serviceNames);
	}

	private static void createRandomQuestionAnswer(Triggerr trigger, int feedbackId, LocalDate date) {
		Question question = trigger.getQuestion();
		Integer id = trigger.getId();
		int grade = gen.nextInt(MAX_GRADE);
		Integer qaId = qualityOrm.addQuestionAnswer(question.getName(), feedbackId, grade);
		if (grade != 0) {
			if (grade < MIN_GRADE_POSITIVE) {
//				System.out.println(trigger.getNumNegative() + " " + trigger.getOverAllNum());
//				recalculNumbers(trigger,date,MIN_GRADE_POSITIVE);
//				System.out.println(trigger.getNumNegative() + " " + trigger.getOverAllNum() + "***********************");
				qualityOrm.setCommentToQa(qaId, "bad answer");
				qualityOrm.increaseNegativForTrigger(id);
				if (trigger.getNumNegative() >= getThresold(trigger)) {
					createTicket(feedbackId, question);
				}
			} else
				qualityOrm.incresePositiveForTrigger(id);
		}
		qualityOrm.increseOverAllNumForTrigger(id);
	}

	private static void createTicket(int feedbackId, Question question) {
		if (noOpenTickets(question)) {
			Ticket ticket = new Ticket(qualityOrm.getDateFromFeedback(feedbackId));
			ticket.setStatus("open");
			qualityOrm.addTicket(ticket, question.getName());
		}
	}

//	private static void recalculNumbers(Triggerr trigger, LocalDate date, int minGradePositive) {
//		date = date.minusDays(PERIOD);
//		String dateStr = date.toString();
//		if(date.isAfter(startDate)){
//			int triggerId = trigger.getId();
//			trigger.setNumNegative(qualityOrm.getNumNegative(triggerId, dateStr, minGradePositive));
//			trigger.setNumPositive(qualityOrm.getNumPositive(triggerId, dateStr, minGradePositive));
//			trigger.setOverAllNum(qualityOrm.getOverAllNum(triggerId, dateStr, minGradePositive));
//		}
//	}

	private static boolean noOpenTickets(Question question) {
		Set<Ticket> tickets = question.getTickets();
		if (tickets != null && !tickets.isEmpty())
			for (Ticket ticket : tickets) {
				if (!ticket.getStatus().equals("close"))
					return false;
			}
		return true;
	}

	private static int getThresold(Triggerr trigger) {
		int num = trigger.getOverAllNum();
		if (num < MIN_N_TO_OPEN_TICKET)
			return MIN_N_TO_OPEN_TICKET;
		return num * trigger.getThreshold() / 100;
	}

	private static void CreateRandomClient(Set<Integer> setFeedbackIds) {
		qualityOrm.addClient(++idClient, "name" + idClient, gen.nextInt(99999) + "-" + gen.nextInt(99999),
				"name" + idClient + "@gmail.com", setFeedbackIds);
	}

	private static Integer createRandomFeedback(String serviceName, LocalDate date) {
		Feedback feedback = qualityOrm.addFeedback(serviceName, date);
		Set<Triggerr> triggers = feedback.getService().getTriggers();
		for (Triggerr trigger : triggers) {
			createRandomQuestionAnswer(trigger, feedback.getId(),date);
		}
		return feedback.getId();
	}

	private static LocalDate createDate(int day) {
		
		return startDate.plusDays(day);
	}

	private static void createRandomService(int i, int idEmployee, HashSet<Integer> idTriggers) {
		Service service = new Service("service #" + i);
		qualityOrm.addService(service, idEmployee, idTriggers);
	}

	private static Integer createRandomEmployee() {
		qualityOrm.addEmployee(++idEmployee, "employee" + idEmployee, null);
		qualityOrm.addEmployee(++idEmployee, "employee" + idEmployee, idEmployee - 1);
		return idEmployee;
	}

	private static Integer createRandomTrigger(String questionName) {
		return qualityOrm.addTrigger(questionName, MIN_THRESHOLD + gen.nextInt(MAX_THRESHOLD - MIN_THRESHOLD), PERIOD);
	}

	private static void createRandomQuestion(int i) {
		qualityOrm.addQuestion("question" + i, "text" + i);
	}
}