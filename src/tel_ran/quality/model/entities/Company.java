package tel_ran.quality.model.entities;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Company {
	@Id
	String name;
	@OneToMany
	Set<Service> services;
	public Company(String name) {
		super();
		this.name = name;
	}
	public Company() {
		super();
	}
	public Set<Service> getServices() {
		return services;
	}
	public void setServices(Set<Service> services) {
		this.services = services;
	}
	public void addServices(Service service) {
		this.services.add(service);
	}
	public String getName() {
		return name;
	}
	@Override
	public String toString() {
		return "Company [name=" + name + ", services=" + services + "]";
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
		Company other = (Company) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}	
}
