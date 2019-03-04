package capgemini.stage.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import capgemini.stage.controller.dto.DTOEmployee;
import capgemini.stage.controller.dto.JSONCredentialInfos;
import capgemini.stage.model.city.City;
import capgemini.stage.model.city.ICityRepository;
import capgemini.stage.model.employee.Employee;
import capgemini.stage.model.employee.IEmployeeRepository;
import capgemini.stage.model.organization.IOrganizationRepository;
import capgemini.stage.model.organization.Organization;
import capgemini.stage.model.role.IRoleRepository;

@CrossOrigin(origins = "http://localhost:8282", allowCredentials = "true")
@RestController
@PreAuthorize("hasRole('ROLE_employee')")
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	IEmployeeRepository employees;

	@Autowired
	ICityRepository citys;

	@Autowired
	IOrganizationRepository organizations;

	@Autowired
	IRoleRepository roles;

	@PutMapping("/put/{id}")
	public ResponseEntity<?> updateEmployees(@RequestBody DTOEmployee employee) {

		City city = citys.findById(employee.getIdCity()).get();
		Organization entity = organizations.findById(employee.getIdOrganization()).get();
		Employee john = employees.findById(employee.getId()).get();
		john.setCity(city);
		john.setOrganization(entity);
		john.setFirstname(employee.getFirstname());
		john.setLastname(employee.getLastname());
		john.setLogin(employee.getLogin());
		john.setMail(employee.getMail());
		john.setPasswordHash(employee.getPasswordHash());
		return new ResponseEntity<Employee>(employees.save(john), HttpStatus.ACCEPTED);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<?> getOne(@PathVariable long id) {
		Employee john = null;
		if (employees.findById(id) != null) {
			john = employees.findById(id).get();
		}
		return new ResponseEntity<Employee>((john), HttpStatus.ACCEPTED);
	}

	@GetMapping("/me")
	public ResponseEntity<?> me() {
		JSONCredentialInfos loginInfo = new JSONCredentialInfos();
		String login = SecurityContextHolder.getContext().getAuthentication().getName();
		loginInfo.setLogin(login);
		Set<String> roles = AuthorityUtils.authorityListToSet(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
		loginInfo.setRoles(roles);
		
		return new ResponseEntity<JSONCredentialInfos>(loginInfo,HttpStatus.OK);
	}

}
