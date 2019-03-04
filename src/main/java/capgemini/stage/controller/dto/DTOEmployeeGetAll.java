package capgemini.stage.controller.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class DTOEmployeeGetAll {
	
	private long id;
	
	private LocalDateTime creationDate;
	
	private boolean actif;

	private String lastname;
	
	private String firstname;
	
	private String login;
	
	private String mail;
	
	private String City;
	
	private String Organization;
	
}
