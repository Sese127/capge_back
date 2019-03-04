package capgemini.stage.model.employee;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import capgemini.stage.model.city.City;
import capgemini.stage.model.organization.Organization;
import capgemini.stage.model.role.Role;
import capgemini.stage.model.user.AbstractUser;
import lombok.Getter;
import lombok.Setter;



@Entity
public class Employee extends AbstractUser{

	
	@Getter @Setter
	@NotEmpty
	private String lastname;
	
	@Getter @Setter
	@NotEmpty
	private String firstname;
	
	@Getter @Setter
	@NotNull
	@OneToOne
	private Organization organization;
	
	@Getter @Setter
	@NotNull
	@OneToOne
	private City city;
	
	
	@ManyToMany(cascade=CascadeType.MERGE)
	@JoinTable(name = "employee_role")
	private Set<Role> roles = new HashSet<Role>();
	
	
	public Employee() {
	}

	public Employee(@NotEmpty String login, @NotEmpty String passwordHash, @NotEmpty String lastname,
			@NotEmpty String firstname, @NotEmpty String mail, boolean actif, @NotNull Organization organization,
			@NotNull City city, List<Role> roles) {
		setLogin(login);
		setPasswordHash(passwordHash);
		this.lastname = lastname;
		this.firstname = firstname;
		setMail(mail);
		setActif(actif);
		this.organization = organization;
		this.city = city;
		for (Role role: roles) {
			this.addRole(role);
		}
	}

	public Employee(@NotEmpty String login, @NotEmpty String passwordHash, @NotEmpty String lastname,
			@NotEmpty String firstname, @NotEmpty String mail, boolean actif, @NotNull Organization organization,
			@NotNull City city) {
		setLogin(login);
		setPasswordHash(passwordHash);
		this.lastname = lastname;
		this.firstname = firstname;
		setMail(mail);
		setActif(actif);
		this.organization = organization;
		this.city = city;
		}
	


	public void addRole(Role role) {
			this.roles.add(role);
	}

	public void removeRole(Role role) {
		if (role != null)
			this.roles.remove(role);
	}

	public List<Role> getRoles() {	
		return new ArrayList<Role>(roles);
	}


}

