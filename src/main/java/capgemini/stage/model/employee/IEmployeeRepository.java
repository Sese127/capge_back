package capgemini.stage.model.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import java.lang.String;
import java.util.List;

import capgemini.stage.model.employee.Employee;

public interface IEmployeeRepository extends JpaRepository<Employee, Long> {

	Employee findByLastname(String lastname);

	List <Employee>  findByActifFalseOrderByLoginAsc();

	Employee findByLogin(String username);

	boolean existsByLogin(String login);

	boolean existsByMail(String mail);

	Employee findByMail(String mail);


	
}
