package tel_ran.quality.model.dao;

import java.time.LocalDate;
import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import tel_ran.quality.model.entities.*;

public class QualityOrm {

	@PersistenceContext(unitName = "springHibernate", type = PersistenceContextType.EXTENDED)
	EntityManager em;

	@Transactional
	public boolean addQuestion(String questionName, String text) {
		if(questionName == null || text == null){
			System.out.println("f0");
			return false;
		}
		Question question = new Question(questionName, text);
		if (question == null || em.find(Question.class, question.getName()) != null){
			System.out.println("f1");
			return false;
		}
		em.persist(question);
		return true;
	}

	@Transactional
	public Integer addTrigger(String questionName, int threshold, int period) {
		if(questionName == null){
			System.out.println("n100");
			return null;
		}
		Triggerr trigger = new Triggerr(threshold, period);
		Question question = em.find(Question.class, questionName);
		if (question == null){
			System.out.println("101");
			return null;
		}
		if (trigger == null || em.find(Triggerr.class, trigger.getId()) != null){
			System.out.println("n102");
			return null;
		}
		trigger.setQuestion(question);
		em.persist(trigger);
		return trigger.getId();
	}

	@Transactional
	public boolean addEmployee(int id, String name, Integer idManager) {
		Employee manager = null;
		if (idManager != null) {
			manager = em.find(Employee.class, idManager);
			if (manager == null){
				System.out.println("f4");
				return false;
			}
		}
		Employee employee = new Employee(id, name);
		if (employee == null || em.find(Employee.class, employee.getId()) != null){
			System.out.println("f5");
			return false;
		}
		employee.setManager(manager);
		em.persist(employee);
		return true;
	}
	
	@Transactional
	public boolean addService(Service service, int idEmployee, HashSet<Integer> idTriggers) {
		if (service == null || em.find(Service.class, service.getName()) != null){
			System.out.println("f6");
			return false;
		}
		Employee employee = em.find(Employee.class, idEmployee);
		if (employee == null){
			System.out.println("f7");
			return false;
		}
		service.setEmployee(employee);
		HashSet<Triggerr> triggers	= getTriggers(idTriggers);
		if (triggers == null || triggers.isEmpty()){
			System.out.println("f8");
			return false;
		}
		service.setTriggers(triggers);
		em.persist(service);
		return true;
	}

	private HashSet<Triggerr> getTriggers(HashSet<Integer> idTriggers) {
		HashSet<Triggerr> res = new HashSet<>();
		for(Integer id : idTriggers){
			Triggerr trigger = null;
			if(id != null)
				trigger = em.find(Triggerr.class, id);
			if(trigger != null)
				res.add(trigger);
		}
		return res;
	}
	
	@Transactional
	public Feedback addFeedback(String serviceName, LocalDate date) {
		if(serviceName == null){
			System.out.println("f9");
			return null;
		}
		Service service = em.find(Service.class, serviceName);
		if (service == null){
			System.out.println("f10");
			return null;
		}
		em.refresh(service);
		Feedback feedback = new Feedback(date);
		feedback.setService(service);
		em.persist(feedback);
		service.addFeedback(feedback);;
		return feedback;
	}

	public boolean addClient(int id, String name, String phoneNumber, 
										String email, Set<Integer> feedbackIds) {
		Client client = new Client(id,name,phoneNumber,email);
		if (client == null || em.find(Client.class, client.getId()) != null){
			System.out.println("f11");
			return false;
		}
		Set<Feedback> feedbacks = getSetFeedbacks(feedbackIds);
		client.setFeedbacks(feedbacks);
		em.persist(client);
		return true;
	}

	private Set<Feedback> getSetFeedbacks(Set<Integer> feedbackIds) {
		HashSet<Feedback> res = new HashSet<>();
		for(Integer id : feedbackIds){
			Feedback feedback = null;
			if(id != null)
				feedback = em.find(Feedback.class, id);
			if(feedback != null)
				res.add(feedback);
		}
		return res;
	}

//	@SuppressWarnings("unchecked")
//	public List<Client> getAllClients() {
//		Query query = em.createQuery("select c from Client c");
//		List<Client> res = (List<Client>) query.getResultList();
//		return res;
//	}

	@Transactional
	public Integer addQuestionAnswer(String questionName, int idFeedback, int grade) {
		Question question = em.find(Question.class, questionName);
		if (question == null){
			System.out.println("n1");
			return null;
		}
		QuestionAnswer qa = new QuestionAnswer(question,grade);
		em.persist(qa);
		
		Feedback feedback = em.find(Feedback.class, idFeedback);
		if (feedback == null){
			System.out.println("n2");
			return null;
		}
		em.refresh(feedback);
		feedback.addQuestionAnswers(qa);
		return qa.getId();
	}
	
//	@Transactional
//	public boolean setGradeToQa(Integer idQa, int grade) {
//		QuestionAnswer qa = em.find(QuestionAnswer.class, idQa);
//		if (qa == null){
//			System.out.println("f13");
//			return false;
//		}
//		em.refresh(qa);
//		qa.setGrade(grade);
//		return true;			
//	}
	@Transactional
	public boolean setCommentToQa(Integer idQa, String comment) {
		QuestionAnswer qa = em.find(QuestionAnswer.class, idQa);
		if (qa == null){
			System.out.println("f14");
			return false;
		}
		em.refresh(qa);
		qa.setComment(comment);
		return true;
	}
	
	@Transactional
	public boolean incresePositiveForTrigger(int idTrigger) {
		Triggerr trigger = em.find(Triggerr.class, idTrigger);
		if (trigger == null){
			System.out.println("f15");
			return false;
		}
		em.refresh(trigger);
		trigger.addPositive();
		return true;
	}
	
	@Transactional
	public boolean increaseNegativForTrigger(int idTrigger) {
		Triggerr trigger = em.find(Triggerr.class, idTrigger);
		if (trigger == null){
			System.out.println("f16");
			return false;
		}
		em.refresh(trigger);
		trigger.addNegative();	
		return true;
	}
	
	@Transactional
	public boolean addCompany(String name, Set<String> serviceNames) {
		if(name == null || serviceNames == null)
			return false;
		Set<Service> services = getServices(serviceNames);
		Company company = new Company(name);
		if (company == null || em.find(Company.class, company.getName()) != null){
			System.out.println("f111");
			return false;
		}
		company.setServices(services);
		em.persist(company);
		return true;
	}

	private Set<Service> getServices(Set<String> serviceNames) {
		HashSet<Service> res = new HashSet<>();
		for(String name : serviceNames){
			Service service = null;
			if(name != null)
				service = em.find(Service.class, name);
			if(service != null)
				res.add(service);
		}
		return res;
	}
	@Transactional
	public boolean increseOverAllNumForTrigger(int id) {
		Triggerr trigger = em.find(Triggerr.class, id);
		if (trigger == null){
			System.out.println("f16");
			return false;
		}
		em.refresh(trigger);
		trigger.addOverAllNum();	
		return true;
	}

	public LocalDate getDateFromFeedback(int feedbackId) {
		Feedback feedback = em.find(Feedback.class, feedbackId);
		if(feedback == null){
			System.out.println("n200");
			return null;
		}
		return feedback.getDate();
	}
	
	@Transactional
	public boolean addTicket(Ticket ticket, String questionName) {
		if(questionName == null){
			System.out.println("n1001");
			return false;
		}
		Question question = em.find(Question.class, questionName);
		if (question == null){
			System.out.println("1011");
			return false;
		}
		em.refresh(question);
		if(ticket == null || em.find(Ticket.class, ticket.getId()) != null) {
			System.out.println("f201");
			return false;
		}
		em.persist(ticket);
		question.addTicket(ticket);
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public List<Ticket> getListOfTickets() {
		Query query = em.createQuery("select t from Ticket t");
		List<Ticket> res = (List<Ticket>) query.getResultList();
		return res;
	}
	
	@Transactional
	public boolean setClose(int idTicket, LocalDate date) {
		Ticket ticket = em.find(Ticket.class, idTicket);
		if (ticket == null){
			System.out.println("f166");
			return false;
		}
		em.refresh(ticket);
		ticket.setStatus("close");
		ticket.setCloseDate(date);
		return true;
	}
	
	@Transactional
	public boolean setInProgress(int idTicket, LocalDate date) {//date?
		Ticket ticket = em.find(Ticket.class, idTicket);
		if (ticket == null){
			System.out.println("f176");
			return false;
		}
		em.refresh(ticket);
		ticket.setStatus("inProgress");
		return true;
	}
	
//	@Transactional
//	public boolean recalculNumbers(Triggerr trigger) {
//		trigger = em.find(Triggerr.class, trigger.getId());
//		if (trigger == null){
//			System.out.println("f316");
//			return false;
//		}
//		em.refresh(trigger);
//		trigger.setNumNegative(0);
//		trigger.setNumPositive(0);
//		trigger.setOverAllNum(0);
//		return true;
//	}

//	public int getNumNegative(int triggerId, String date, int minGradePositive) {		
//		Query query = em.createQuery("select count(qa) from Feedback f join "
//				+ "f.questionAnswers qa join f.service s join s.triggers t where f.date>='"
//				+ date + "' and t.id=" + triggerId + " and qa.grade<" 
//				+ minGradePositive + " and not qa.grade=0");
//		return (Integer) query.getSingleResult();
//		
//	}
//
//	public int getNumPositive(int triggerId, String date, int minGradePositive) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	public int getOverAllNum(int triggerId, String date, int minGradePositive) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
}
