package tel_ran.quality.model.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Triggerr {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	int numPositive;
	int numNegative;
	int overAllNum;
	int threshold;
	int period;
	@ManyToOne
	Question question;
	
	public Triggerr() {
		super();
	}

	public Triggerr(int threshold, int period) {
		super();
		this.period = period;
		this.threshold = threshold;
		this.numPositive = 0;
		this.numNegative = 0;
		this.overAllNum = 0;
	}

	public int getNumPositive() {
		return numPositive;
	}
	
	public int getNumNegative() {
		return numNegative;
	}

	public int getOverAllNum() {
		return overAllNum;
	}

	public int getId() {
		return id;
	}

	public int getThreshold() {
		return threshold;
	}

	public Question getQuestion() {
		return question;
	}
	
	public void setQuestion(Question question) {
		this.question = question;
	}
	
	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	@Override
	public String toString() {
		return "Trigger [id=" + id + ", numPositive=" + numPositive + ", numNegative=" + numNegative + ", overAllNum="
				+ overAllNum + ", threshold=" + threshold + ", period=" + period + ", question=" + question + "]";
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
		Triggerr other = (Triggerr) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public void addPositive() {
		this.numPositive++;
	}
	
	public void addNegative() {
		this.numNegative++;
	}
	
	public void addOverAllNum() {
		this.overAllNum++;
	}


	public void setNumNegative(int numNegative) {
		this.numNegative = numNegative;
	}

	public void setNumPositive(int numPositive) {
		this.numPositive = numPositive;
	}

	public void setOverAllNum(int overAllNum) {
		this.overAllNum = overAllNum;
	}

	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}
}
