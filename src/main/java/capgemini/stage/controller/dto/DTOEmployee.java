package capgemini.stage.controller.dto;

import lombok.Data;

@Data
public class DTOEmployee {
	
	private long id;
	
	private String lastname;
	
	private String firstname;
	
	private String login;
	
	private String passwordHash;
	
	private String mail;
	
	private long idCity;
	
	private long idOrganization;


}
