package jp.co.axa.apidemo.services;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import jp.co.axa.apidemo.entities.Employee;

/**
 * Test for the EmployeeServiceImplTest
 * @author li.han
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeServiceImplTest {

	@Autowired
	EmployeeService employeeService;

	private Employee employee1 = new Employee();
	private Employee employee2 = new Employee();
	private Employee employee3 = new Employee();
	private Employee employee4 = new Employee();

	@Before
	public void Setup() {
		employee1.setDepartment("testDep1");
		employee1.setName("testUser1");
		employee1.setSalary(1000);

		employee2.setDepartment("testDep2");
		employee2.setName("testUser2");
		employee2.setSalary(2000);

		employee3.setDepartment("testDep3");
		employee3.setId(3001L);
		employee3.setName("testUser3");
		employee3.setSalary(3000);
	}

	//Test the function all together will be better for this task
	@Test
	public void testEmployeeService() {
		
		/////////////////test with employeeService.saveEmployee 
		/////////////////test with employeeService.retrieveEmployees 
		/////////////////test with employeeService.getEmployees
		
		// Case 1.1 Test Save one data
		// Case 1.2 Get employee by Id 1
		// Case 1.3 Retrieve employee
		employeeService.saveEmployee(employee1);

		Employee employeeGet = employeeService.getEmployee(1L);
		Assert.assertEquals("testDep1", employeeGet.getDepartment());
		Assert.assertEquals(Long.valueOf(1), employeeGet.getId());
		Assert.assertEquals("testUser1", employeeGet.getName());
		Assert.assertEquals(Integer.valueOf(1000), employeeGet.getSalary());

		List<Employee> result = employeeService.retrieveEmployees();
		Assert.assertEquals(1, result.size());
		Assert.assertEquals("testDep1", result.get(0).getDepartment());
		Assert.assertEquals(Long.valueOf(1), result.get(0).getId());
		Assert.assertEquals("testUser1", result.get(0).getName());
		Assert.assertEquals(Integer.valueOf(1000), result.get(0).getSalary());

		// Case 2.1 Test Save one more data
		// Case 2.2 Get employee by Id 1 and id 2
		// Case 2.3 Retrieve employee
		employeeService.saveEmployee(employee2);
		employeeGet = employeeService.getEmployee(1L);
		Assert.assertEquals("testDep1", employeeGet.getDepartment());
		Assert.assertEquals(Long.valueOf(1), employeeGet.getId());
		Assert.assertEquals("testUser1", employeeGet.getName());
		Assert.assertEquals(Integer.valueOf(1000), employeeGet.getSalary());
		// Get by Id
		employeeGet = employeeService.getEmployee(2L);
		Assert.assertEquals("testDep2", employeeGet.getDepartment());
		Assert.assertEquals(Long.valueOf(2), employeeGet.getId());
		Assert.assertEquals("testUser2", employeeGet.getName());
		Assert.assertEquals(Integer.valueOf(2000), employeeGet.getSalary());

		result = employeeService.retrieveEmployees();
		Assert.assertEquals(2, result.size());
		Assert.assertEquals("testDep1", result.get(0).getDepartment());
		Assert.assertEquals(Long.valueOf(1), result.get(0).getId());
		Assert.assertEquals("testUser1", result.get(0).getName());
		Assert.assertEquals(Integer.valueOf(1000), result.get(0).getSalary());
		Assert.assertEquals(2, result.size());
		Assert.assertEquals("testDep2", result.get(1).getDepartment());
		Assert.assertEquals(Long.valueOf(2), result.get(1).getId());
		Assert.assertEquals("testUser2", result.get(1).getName());
		Assert.assertEquals(Integer.valueOf(2000), result.get(1).getSalary());

		// Case 3.1 Test with save data with id has been setup with 3001L
		// Case 3.2 Get employee by Id 3,
		// Case 3.3 Retrieve employee with size 3
		employeeService.saveEmployee(employee3);

		employeeGet = employeeService.getEmployee(3L);
		Assert.assertEquals("testDep3", employeeGet.getDepartment());
		Assert.assertEquals(Long.valueOf(3), employeeGet.getId());
		Assert.assertEquals("testUser3", employeeGet.getName());
		Assert.assertEquals(Integer.valueOf(3000), employeeGet.getSalary());
		result = employeeService.retrieveEmployees();
		Assert.assertEquals(3, result.size());

		// Case 4.1 Test saving with employ4 which has empty value
		// Case 4.2 Get employee by id 4
		// Case 4.3 Retrieve employee with size 4
		employeeService.saveEmployee(employee4);

		employeeGet = employeeService.getEmployee(4L);
		Assert.assertNull(employeeGet.getDepartment());
		Assert.assertEquals(Long.valueOf(4), employeeGet.getId());
		Assert.assertNull(employeeGet.getName());
		Assert.assertNull(employeeGet.getSalary());
		result = employeeService.retrieveEmployees();
		Assert.assertEquals(4, result.size());

		// improved this part with more logic 
		// Case 5.1 save null
		// Case 5.2 Retrieve employee with size 4
		employeeService.saveEmployee(null);
		result = employeeService.retrieveEmployees();
		Assert.assertEquals(4, result.size());
		
		// improved this part with more logic to avoid update exist data 
		// Case 6.1 save with same id (emplyeeId = 1)
		// Case 6.2 Retrieve employee with size 4
		// Case 6.3 the data in employeeid=1 will not change 
		employee4.setId(1L);
		employeeService.saveEmployee(employee4);

		employeeGet = employeeService.getEmployee(1L);
		Assert.assertEquals("testDep1", employeeGet.getDepartment());
		Assert.assertEquals(Long.valueOf(1), employeeGet.getId());
		Assert.assertEquals("testUser1", employeeGet.getName());
		Assert.assertEquals(Integer.valueOf(1000), employeeGet.getSalary());
		
		// improved with try-catch
		// Case 7.1 test with get with id not exist 
		employeeGet = employeeService.getEmployee(100L);
		Assert.assertNull(employeeGet);

		///////////////Test employeeService.deleteEmployee
		
		// Case8.1 test with Delete with id 4
		// Case8.2 Retrieve employee with size 3
		// Case8.3 Retrieve employee with id 1,2,3
		employeeService.deleteEmployee(4L);
		result = employeeService.retrieveEmployees();
		Assert.assertEquals(3, result.size());
		Assert.assertEquals(Long.valueOf(1), result.get(0).getId());
		Assert.assertEquals(Long.valueOf(2), result.get(1).getId());
		Assert.assertEquals(Long.valueOf(3), result.get(2).getId());

		// Case 9.1 Test with save data after delete
		// Case 9.2 Retrieve employee with size 3
		// Case 9.3 Retrieve employee with id 1,2,3,5
		employee4.setId(null);
		employeeService.saveEmployee(employee4);
		result = employeeService.retrieveEmployees();
		Assert.assertEquals(4, result.size());
		Assert.assertEquals(Long.valueOf(1), result.get(0).getId());
		Assert.assertEquals(Long.valueOf(2), result.get(1).getId());
		Assert.assertEquals(Long.valueOf(3), result.get(2).getId());
		Assert.assertEquals(Long.valueOf(5), result.get(3).getId());

		// Improve with try-catch the cases
		// Case 10.1 Test Delete id not exist
		// Case 10.2 Retrieve employee with size 4
		// Case 10.3 Retrieve employee with id 1,2,3,5
		employeeService.deleteEmployee(100L);
		result = employeeService.retrieveEmployees();
		Assert.assertEquals(4, result.size());
		Assert.assertEquals(Long.valueOf(1), result.get(0).getId());
		Assert.assertEquals(Long.valueOf(2), result.get(1).getId());
		Assert.assertEquals(Long.valueOf(3), result.get(2).getId());
		Assert.assertEquals(Long.valueOf(5), result.get(3).getId());
		
		///////////////Test employeeService.updateEmployee
		
		// Case 11.1 Normal cases update employee with id 5
		// Case 11.2 Retrieve employee with size 4
	    // Case 11.3 Retrieve employee with id 1,2,3,5
		// Case 11.4 Get with updated Value 
		employee4.setDepartment("testDep5");
		employee4.setId(5L);
		employee4.setName("testUser5");
		employee4.setSalary(5000);
		employeeService.updateEmployee(employee4, 5L);
		result = employeeService.retrieveEmployees();
		Assert.assertEquals(4, result.size());
		Assert.assertEquals(Long.valueOf(1), result.get(0).getId());
		Assert.assertEquals(Long.valueOf(2), result.get(1).getId());
		Assert.assertEquals(Long.valueOf(3), result.get(2).getId());
		Assert.assertEquals(Long.valueOf(5), result.get(3).getId());
		employeeGet = employeeService.getEmployee(5L);
		Assert.assertEquals("testDep5", employeeGet.getDepartment());
		Assert.assertEquals(Long.valueOf(5), employeeGet.getId());
		Assert.assertEquals("testUser5", employeeGet.getName());
		Assert.assertEquals(Integer.valueOf(5000), employeeGet.getSalary());
		
		// Case 12.1 Normal cases update employee with id not exist
		// Case 12.2 Retrieve employee with size 4
	    // Case 12.3 Retrieve employee with id 1,2,3,5
		// Case 12.4 it should be no changes for the data with id=5
		employee4.setDepartment("testDep6");
		employee4.setId(6L);
		employee4.setName("testUser6");
		employee4.setSalary(6000);
		employeeService.updateEmployee(employee4, 6L);
		result = employeeService.retrieveEmployees();
		Assert.assertEquals(4, result.size());
		Assert.assertEquals(Long.valueOf(1), result.get(0).getId());
		Assert.assertEquals(Long.valueOf(2), result.get(1).getId());
		Assert.assertEquals(Long.valueOf(3), result.get(2).getId());
		Assert.assertEquals(Long.valueOf(5), result.get(3).getId());
		employeeGet = employeeService.getEmployee(5L);
		Assert.assertEquals("testDep5", employeeGet.getDepartment());
		Assert.assertEquals(Long.valueOf(5), employeeGet.getId());
		Assert.assertEquals("testUser5", employeeGet.getName());
		Assert.assertEquals(Integer.valueOf(5000), employeeGet.getSalary());
		
		// Case 13.1 Normal cases update employee with id is not same to object 
		// Case 13.2 Retrieve employee with size 4
	    // Case 13.3 Retrieve employee with id 1,2,3,5
		// Case 13.4 it should be no changes for the data with id=5
		employee4.setDepartment("testDep6");
		employee4.setId(6L);
		employee4.setName("testUser6");
		employee4.setSalary(6000);
		employeeService.updateEmployee(employee4, 5L);
		result = employeeService.retrieveEmployees();
		Assert.assertEquals(4, result.size());
		Assert.assertEquals(Long.valueOf(1), result.get(0).getId());
		Assert.assertEquals(Long.valueOf(2), result.get(1).getId());
		Assert.assertEquals(Long.valueOf(3), result.get(2).getId());
		Assert.assertEquals(Long.valueOf(5), result.get(3).getId());
		employeeGet = employeeService.getEmployee(5L);
		Assert.assertEquals("testDep5", employeeGet.getDepartment());
		Assert.assertEquals(Long.valueOf(5), employeeGet.getId());
		Assert.assertEquals("testUser5", employeeGet.getName());
		Assert.assertEquals(Integer.valueOf(5000), employeeGet.getSalary());
		
		// improved with more logic
		// Case 14.1 Normal cases update employee with null
		// Case 14.2 Retrieve employee with size 6
		employeeService.updateEmployee(null, 5L);
		result = employeeService.retrieveEmployees();
		Assert.assertEquals(4, result.size());
	}
}
