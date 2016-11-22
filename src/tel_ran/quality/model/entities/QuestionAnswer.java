package tel_ran.quality.model.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class QuestionAnswer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	int grade;
	String comment;
	@ManyToOne
	Question question;
	
	public QuestionAnswer() {
		super();
	}

	public QuestionAnswer(Question question, int grade) {
		super();
		this.question = question;
		this.grade = grade;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
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

	public Question getQuestion() {
		return question;
	}

	@Override
	public String toString() {
		return "QuestionAnswer [id=" + id + ", grade=" + grade + ", comment=" + comment + ", question=" + question
				+ "]";
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
		QuestionAnswer other = (QuestionAnswer) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
}
