package capgemini.stage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import capgemini.stage.model.charity.Charity;
import capgemini.stage.model.charity.ICharityRepository;
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

@RunWith(SpringRunner.class)
@SpringBootTest
public class Capgemini2ApplicationTests {

	@Autowired
	private IEmployeeRepository employees;
	
	@Autowired
	private ICityRepository citys;
	
	@Autowired
	private IOrganizationRepository organizations;
	
	@Autowired
	private IRoleRepository roles;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private ICharityRepository charities;
	
	@Autowired
	private JWTService jwtService;
	
	@Before
	public void reset() {
//		employees.deleteAll();
	}
	@Test
	public void contextLoads() {
		City paris = citys.findById((long) 1).get();
		Organization sogeti = organizations.findById((long) 2).get();
		
		Employee paul = new Employee("a", "b", " ", " ", " ", true, sogeti, paris);
		paul.addRole(roles.findById(1L).get());
		employees.save(paul);
		
		assertEquals(employees.count(), 1);
	}
	
	@Test
	public void delete() {
		City paris = citys.findById((long) 1).get();
		Organization sogeti = organizations.findById((long) 2).get();
		
		Employee paul = new Employee("a", "b", " ", " ", " ", true, sogeti, paris);
		paul.addRole(roles.findById(1L).get());
		employees.save(paul);
		employees.delete(paul);
		
		assertEquals(employees.count(), 0);
	}
	
	@Test
	public void findbylastname() {
		City paris = citys.findById((long) 1).get();
		Organization sogeti = organizations.findById((long) 2).get();
		
		Employee paul = new Employee("paul", "paul", "paul", "paul", "paul@paul", true, sogeti, paris);
		paul.addRole(roles.findById(1L).get());
		employees.save(paul);
		Employee jack = employees.findByLastname("paul");
		employees.save(jack);
		assertEquals(paul.getLastname(), jack.getLastname());
	}
	
	@Test
	public void update() {
		City paris = citys.findById((long) 1).get();
		Organization sogeti = organizations.findById((long) 2).get();
		
		Employee paul = new Employee("paul", "paul", "paul", "paul", "paul@paul", true, sogeti, paris);
		paul.addRole(roles.findById(1L).get());
		employees.save(paul);
		Employee jack = employees.findByLastname("paul");
		jack.setLastname("jack");
		employees.save(jack);
	}	
	
	@Test
	public void token() {	
		DTOLogin login = new DTOLogin("a","a");
		Employee bob = new Employee("a", "a", "a", "a", "a", true, organizations.findById(1L).get(), citys.findById(1L).get());
		employees.save(bob);
		
		if (!employees.existsByMail(login.getMail())) {
			ResponseEntity.status(HttpStatus.FORBIDDEN).body("Bad user");
		}

		Employee josh = employees.findByMail(login.getMail());

		if (!josh.isActif()) {
			ResponseEntity.status(HttpStatus.FORBIDDEN).body("Disabled");
		}

		if (!encoder.matches(login.getPassword(), josh.getPasswordHash())) {
			ResponseEntity.status(HttpStatus.FORBIDDEN).body("Bad password");
		}

		List<String> roles = new ArrayList<String>();
		for (Role role : josh.getRoles()) {
			roles.add(role.getLabel());
		}

		String jwt = jwtService.createJWT(josh.getLogin(), roles);
	}

	@Test
	public void findByMail() {
		Employee john = new Employee("a", "a", "a", "a", "a", true, organizations.findById(1L).get(), citys.findById(1L).get());
		employees.save(john);
		Employee brad = employees.findByMail(john.getMail());
		assertEquals(john.getLastname(), brad.getLastname());
	}
	
	@Test
	public void encoder() {
		City one = citys.findById(1L).get();
		Organization two = organizations.findById(1L).get();

		employees.save(new Employee("ztevoz", encoder.encode("password"), "milloz", "ztevoz", "ztevoz@ztevoz.com", true, two, one));
		Employee josh = employees.findByMail("ztevoz@ztevoz.com");
		assertTrue(encoder.matches("password", josh.getPasswordHash()));
		
	}
	
	@Test
	public void role(){
		Employee john = new Employee("a", "a", "a", "a", "a", true, organizations.findById(1L).get(), citys.findById(1L).get());
		john.addRole(roles.findById(1L).get());
		List<String> roles = new ArrayList<String>();
		for (Role role : john.getRoles()) {
			roles.add(role.getLabel());
		}
		employees.save(john);
		
		assertEquals(roles.size(), 1);
	}
	
	@Test
	public void contextLoadsCharity() {
		City paris = citys.findById((long) 1).get();
		
		Charity assoc = new Charity("a", "b", "assoc@live.fr","assoc", paris, true);
		charities.save(assoc);
		
		assertEquals(charities.count(), 1);
	}

}
