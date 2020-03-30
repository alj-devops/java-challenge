package jp.co.axa.apidemo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.errors.EmployeeNotFoundException;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import jp.co.axa.apidemo.services.EmployeeService;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ApiDemoApplicationTests {

	@Autowired
	EmployeeService service;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Before
	public void contextLoads() {
	}

	@Test
	public void checkSaveEmplyee() {
		Employee emp = new Employee(null, "sachin", 1000, "ENTC");
		Employee empcheck = service.saveEmployee(emp);
		assertNotNull(empcheck);
		assertEquals(emp.getName(), empcheck.getName());

		Employee checkget = service.getEmployee(empcheck.getId());

		assertNotNull(checkget);
		assertEquals(emp.getDepartment(), checkget.getDepartment());
		assertEquals(emp.getName(), checkget.getName());

	}

	@Test
	public void checkSaveEmplyeeRepo() {
		Employee emp = new Employee(null, "sachin", 1000, "ENTC");
		Employee empcheck = employeeRepository.save(emp);
		assertNotNull(empcheck);
		assertEquals(emp.getName(), empcheck.getName());

	}

	@Test
	public void checkExceptionEmplyee() {
		Long id = new Long(326326L);
		try {
			service.getEmployee(id);

		} catch (Exception e) {
			assertEquals(EmployeeNotFoundException.class, e.getClass());
			assertEquals("Could not find employee with ID : " + id, e.getMessage());

		}
	}
	
	
	@Test(expected = EmployeeNotFoundException.class)
	public void checkExceptionEmplyeeone() {
		Long id = new Long(326326L);
		service.getEmployee(id);

		
	}
}