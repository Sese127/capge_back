package capgemini.stage.controller.dto;

import lombok.Data;

@Data
public class DTOLogin {

	private String mail;
	
	private String password;

	public DTOLogin(String mail, String password) {
		this.mail = mail;
		this.password = password;
	}
	
	
}
