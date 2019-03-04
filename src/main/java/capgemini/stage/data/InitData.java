package capgemini.stage.data;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import capgemini.stage.model.city.City;
import capgemini.stage.model.city.ICityRepository;
import capgemini.stage.model.employee.Employee;
import capgemini.stage.model.employee.IEmployeeRepository;
import capgemini.stage.model.organization.IOrganizationRepository;
import capgemini.stage.model.organization.Organization;
import capgemini.stage.model.role.IRoleRepository;
import capgemini.stage.model.role.Role;


@Component
public class InitData implements CommandLineRunner{

	@Autowired
	private ICityRepository citys;
	
	@Autowired
	private IOrganizationRepository organizations;
	
	@Autowired
	private IEmployeeRepository employees;
	
	@Autowired
	private IRoleRepository roles;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Override
	public void run(String... args) throws Exception {
		
		if(roles.count() == 0) {
			Role employee = new Role("employee");
			Role admin = new Role("admin");
			Role charity = new Role("charity");
			roles.saveAll(Arrays.asList(employee, admin, charity));
		}
		
		if(citys.count() == 0) {
			
			City paris = new City("Paris");
			City marseille = new City("Marseille");
			City lyon = new City("Lyon");
			
			citys.saveAll(Arrays.asList(paris, marseille, lyon));	
		}
		
		if (organizations.count() == 0) {
			Organization buisness = new Organization("Buisness");
			Organization sogeti = new Organization("Sogeti");
			organizations.saveAll(Arrays.asList(buisness, sogeti));
		}
		if (employees.count() == 0) {
				City paris = citys.findById((long) 1).get();
				Organization sogeti = organizations.findById((long) 2).get();
				
				Employee jack = new Employee("marrio", encoder.encode("password3"), " benoit", " benja", "benoit@mario", true, sogeti, paris);
				jack.addRole(roles.getOne(1L));
				
				Employee ztevoz = new Employee("ztevoz", encoder.encode("password"), "milloz", "ztevoz", "ztevoz@ztevoz.com", true, organizations.findById(1L).get(), citys.findById(1L).get());
				ztevoz.addRole(roles.getOne(1L));
				
				Employee admin = new Employee("admin", encoder.encode("root"), "boby", "dilan", "admin@root", true, sogeti, paris);
				admin.addRole(roles.getOne(2L));
				admin.addRole(roles.getOne(1L));
				
				employees.saveAll(Arrays.asList(jack, ztevoz, admin));
		}
		
		
	}

}
