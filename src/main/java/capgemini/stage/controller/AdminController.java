package capgemini.stage.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import capgemini.stage.controller.dto.DTOEmployee;
import capgemini.stage.controller.dto.DTOEmployeeGetAll;
import capgemini.stage.model.city.ICityRepository;
import capgemini.stage.model.employee.Employee;
import capgemini.stage.model.employee.IEmployeeRepository;
import capgemini.stage.model.organization.IOrganizationRepository;
import capgemini.stage.model.role.IRoleRepository;

@CrossOrigin(origins = "http://localhost:8282", allowCredentials = "true")
@PreAuthorize("hasRole('ROLE_admin')")
@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	IEmployeeRepository employees;
	
	@Autowired
	ICityRepository citys;
	
	@Autowired
	IOrganizationRepository organizations;
	
	@Autowired
	IRoleRepository roles;
 
	@GetMapping("/getAll")
	public ResponseEntity<?> getAll(){
		List<DTOEmployeeGetAll> list = new ArrayList<DTOEmployeeGetAll>();
		
		
		for(Employee emp : employees.findAll())
		{
			DTOEmployeeGetAll dto = new DTOEmployeeGetAll();
			dto.setCity(emp.getCity().getLabel());
			dto.setOrganization(emp.getOrganization().getLabel());
			dto.setFirstname(emp.getFirstname());
			dto.setLastname(emp.getLastname());
			dto.setLogin(emp.getLogin());
			dto.setMail(emp.getMail());
			dto.setId(emp.getId());
			dto.setActif(emp.isActif());
			dto.setCreationDate(emp.getCreationDate());
			list.add(dto);
		}
		return new ResponseEntity<List<DTOEmployeeGetAll>>(list,HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/put")
	public ResponseEntity<?> desactivEmployees(@RequestBody DTOEmployee employee) {
		Employee john = employees.findById(employee.getId()).get();
		john.setActif(false);
		return new ResponseEntity<Employee>(employees.save(john),HttpStatus.ACCEPTED);
	}
	

}
