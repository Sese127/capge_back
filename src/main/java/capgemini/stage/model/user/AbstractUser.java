package capgemini.stage.model.user;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
public abstract class AbstractUser {

	@Getter
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Getter
	private LocalDateTime creationDate = LocalDateTime.now();
	
	@Getter @Setter
	@NotEmpty
	private String login;
	
	@Getter @Setter
	@NotEmpty
	private String passwordHash;
	
	@Getter @Setter
	@NotEmpty
	@Column(unique=true)
	private String mail;
	
	@Getter @Setter
	private boolean actif = true;
	
	
}
