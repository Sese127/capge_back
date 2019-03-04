package capgemini.stage.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import capgemini.stage.controller.dto.DTOEmployee;
import capgemini.stage.controller.dto.DTOLogin;
import capgemini.stage.model.city.City;
import capgemini.stage.model.city.ICityRepository;
import capgemini.stage.model.employee.Employee;
import capgemini.stage.model.employee.IEmployeeRepository;
import capgemini.stage.model.organization.IOrganizationRepository;
import capgemini.stage.model.organization.Organization;
import capgemini.stage.model.role.IRoleRepository;
import capgemini.stage.model.role.Role;
import capgemini.stage.security.JWTService;

@CrossOrigin(origins = "http://localhost:8282", allowCredentials = "true")
@RestController
@RequestMapping("/ano")
public class AnonymousController {

	@Autowired
	IEmployeeRepository employees;
	
	@Autowired
	ICityRepository citys;
	
	@Autowired
	IOrganizationRepository organizations;
	
	@Autowired
	IRoleRepository roles;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	private JWTService jwtService;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	

	@PostMapping("/post")
	public ResponseEntity<?> createEmployees(@RequestBody DTOEmployee employee) {
		City city = citys.findById(employee.getIdCity()).get();
		Organization entity = organizations.findById(employee.getIdOrganization()).get();
		Employee john = new Employee();
		Role roleToAdd = roles.findByLabel("employee");
		john.setCity(city);
		john.setOrganization(entity);
		john.setFirstname(employee.getFirstname());
		john.setLastname(employee.getLastname());
		john.setLogin(employee.getLogin());
		john.setMail(employee.getMail());
		john.addRole(roleToAdd);
		john.setPasswordHash(encoder.encode(employee.getPasswordHash()));
		john.setActif(true);
		return new ResponseEntity<Employee>(employees.save(john),HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/connexion")
	public ResponseEntity<?> connexion(@RequestBody DTOLogin login, HttpServletRequest request) throws Exception {
				
		if (!employees.existsByMail(login.getMail())) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Bad user");
		}

		Employee josh = employees.findByMail(login.getMail());

		if (!josh.isActif()) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Disabled");
		}

		if (!encoder.matches(login.getPassword(), josh.getPasswordHash())) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Bad password");
		}
	
		List<String> roles = new ArrayList<String>();
		for (Role role : josh.getRoles()) {
			roles.add(role.getLabel());
		}


		String jwt = jwtService.createJWT(josh.getLogin(), roles);

		logger.trace("JWT crée pour " + josh.getLogin() + " : " + jwt);
		return ResponseEntity.ok().body(jwt);

	}
	
}
