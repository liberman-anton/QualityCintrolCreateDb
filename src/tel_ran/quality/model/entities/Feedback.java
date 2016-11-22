package tel_ran.quality.model.entities;

import java.time.LocalDate;
import java.util.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Feedback {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	LocalDate date;
	String comment;
	@OneToMany
	Set<QuestionAnswer> questionAnswers;
	@ManyToOne
	Service service;
	
	public Feedback() {
		super();
	}
	public Feedback(LocalDate date) {
		super();
		this.date = date;
	}
	@Override
	public String toString() {
		return "Questionnaire [id=" + id + ", date=" + date + ", comment=" + comment + ", service=" + service + "]";
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getId() {
		return id;
	}
	public Set<QuestionAnswer> getQuestionAnswers() {
		return questionAnswers;
	}
	public Service getService() {
		return service;
	}
	public void setService(Service service) {
		this.service = service;
	}
	public void addQuestionAnswers(QuestionAnswer questionAnswers) {
		this.questionAnswers.add(questionAnswers);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Feedback other = (Feedback) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
}
