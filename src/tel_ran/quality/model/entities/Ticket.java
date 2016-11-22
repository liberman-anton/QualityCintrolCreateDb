package tel_ran.quality.model.entities;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class Ticket {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	LocalDate openDate;
	LocalDate closeDate;
	String status;
	
	public Ticket() {
		super();
	}

	public Ticket(LocalDate openDate) {
		super();
		this.openDate = openDate;
	}

	public LocalDate getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(LocalDate closeDate) {
		this.closeDate = closeDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public LocalDate getOpenDate() {
		return openDate;
	}

	@Override
	public String toString() {
		return "Ticket [id=" + id + ", openDate=" + openDate + ", closeDate=" + closeDate + ", status=" + status + "]";
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
		Ticket other = (Ticket) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
}
