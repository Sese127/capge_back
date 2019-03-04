package capgemini.stage.model.charity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;

import capgemini.stage.model.city.City;
import capgemini.stage.model.user.AbstractUser;
import lombok.Getter;
import lombok.Setter;


@Entity
public class Charity extends AbstractUser{
	@Getter @Setter
	@NotEmpty
	@Column(unique=true)
	private String name;
	
	@Getter @Setter
	@OneToOne
	private City city;
	
	protected Charity() {
		
	}
	
	public Charity(@NotEmpty String login, @NotEmpty String passwordHash, @NotEmpty String mail,String name, City city, boolean actif) {
		setLogin(login);
		setPasswordHash(passwordHash);
		setMail(mail);
		this.name=name;
		this.city = city;
		setActif(actif);
	}
	
}
