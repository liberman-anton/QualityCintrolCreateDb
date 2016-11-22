package tel_ran.quality.model.entities;


import java.util.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


@Entity
public class Service {
	@Id
	String name;
	@ManyToOne
	Employee employee;
	@OneToMany
	Set<Feedback> feedbacks;
	@OneToMany
	Set<Triggerr> triggers;
	
	public Service() {
		super();
	}

	public Set<Triggerr> getTriggers() {
		return triggers;
	}

	public Service(String name) {
		super();
		this.name = name;
	}

	public Set<Feedback> getFeedbacks() {
		return feedbacks;
	}

	public String getName() {
		return name;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public void setTriggers(Set<Triggerr> triggers) {
		this.triggers = triggers;
	}
	
	
	
	@Override
	public String toString() {
		return "Service [name=" + name + ", employee=" + employee + ", triggers=" + triggers + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Service other = (Service) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public void setFeedbacks(Set<Feedback> feedbacks) {
		this.feedbacks = feedbacks;
	}

	public void addFeedback(Feedback feedback) {
		this.feedbacks.add(feedback);
	}
	
}
