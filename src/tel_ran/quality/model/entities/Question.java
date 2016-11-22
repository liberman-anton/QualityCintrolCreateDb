package tel_ran.quality.model.entities;

import java.util.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Question {
	@Id
	String name;
	String text;
	@OneToMany
	Set<Ticket> tickets;
	public Question() {
		super();
	}
	public Question(String name, String text) {
		super();
		this.name = name;
		this.text = text;
		this.tickets = new HashSet<>();
	}
	public Set<Ticket> getTickets() {
		return tickets;
	}
	public void addTicket(Ticket ticket) {
		this.tickets.add(ticket);
	}
	public String getName() {
		return name;
	}
	public String getText() {
		return text;
	}
	@Override
	public String toString() {
		return "Question [name=" + name + ", text=" + text + ", tickets=" + tickets + "]";
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
		Question other = (Question) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}
