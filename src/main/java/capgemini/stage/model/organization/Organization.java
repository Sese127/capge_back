package capgemini.stage.model.organization;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Organization {
	
	@Getter
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@Getter @Setter
	@NotEmpty
	@Column(unique=true)
	private String label;

	public Organization(@NotEmpty String label) {
		this.label = label;
	}

	protected Organization() {
	}
	
	
	
	
}