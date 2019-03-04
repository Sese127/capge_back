package capgemini.stage.model.role;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import capgemini.stage.model.employee.Employee;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Role {
	
	@Getter
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@Getter
	private LocalDateTime creationDate = LocalDateTime.now();
	
	@Getter @Setter
	@Column(unique=true)
	private String label;
	
	@JsonIgnore
	@Getter @Setter
	@ManyToMany(cascade=CascadeType.MERGE, mappedBy="roles")
	private Set<Employee> employees = new HashSet<Employee>();

	public Role(String label) {
		this.label = label;
	}

	protected Role() {
	}

}
