package capgemini.stage.controller.dto;

import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JSONCredentialInfos {

	private String login;
	private Set<String> roles = new HashSet<String>();

}
