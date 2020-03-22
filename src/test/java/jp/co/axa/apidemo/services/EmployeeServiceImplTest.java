package jp.co.axa.apidemo.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doNothing;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.model.EmployeeDTO;
import jp.co.axa.apidemo.repositories.EmployeeRepository;

/**
 * Test class for EmployeeServiceImpl
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeServiceImplTest {

	@Autowired
	private EmployeeService employeeService;

	@MockBean
	private EmployeeRepository employeeRepository;

	private Employee employee1, employee2;

	private List<Employee> employeeList = new ArrayList<>();

	private EmployeeDTO inputEmployee;

	@Before
	public void setUp() {
		employee1 = new Employee(1L, "Harry", 450000, "Sales");
		employee2 = new Employee(2L, "Ron", 350000, "Marketing");
		employeeList.add(employee1);
		employeeList.add(employee2);
		inputEmployee = new EmployeeDTO(2L, "Ron", 350000, "Marketing");
	}

	/**
	 * Test for getEmployee()
	 */
	@Test
	public void getEmployeeTest() {
		Mockito.when(employeeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(employee1));
		EmployeeDTO employeeDTO = employeeService.getEmployee(Mockito.anyLong());
		assertThat(employeeDTO.getId()).isEqualTo(employee1.getId());
		assertThat(employeeDTO.getName()).isEqualTo(employee1.getName());
		assertThat(employeeDTO.getSalary()).isEqualTo(employee1.getSalary());
		assertThat(employeeDTO.getDepartment()).isEqualTo(employee1.getDepartment());
	}

	/**
	 * Test for retrieveEmployees()
	 */
	@Test
	public void retrieveEmployeesTest() {
		Mockito.when(employeeRepository.findAll()).thenReturn(employeeList);
		List<EmployeeDTO> employeeDTOList = employeeService.retrieveEmployees();
		assertFalse(employeeDTOList.isEmpty());
		assertThat(employeeDTOList.size() == 2);
	}

	/**
	 * Test for saveEmployee()
	 */
	@Test
	public void saveEmployeeTest() {
		Mockito.when(employeeRepository.save(employee2)).thenReturn(employee2);
		EmployeeDTO savedEmployee = employeeService.saveEmployee(inputEmployee);
		assertNotNull(savedEmployee);
		assertThat(savedEmployee.getId()).isEqualTo(inputEmployee.getId());
		assertThat(savedEmployee.getName()).isEqualTo(inputEmployee.getName());
		assertThat(savedEmployee.getSalary()).isEqualTo(inputEmployee.getSalary());
		assertThat(savedEmployee.getDepartment()).isEqualTo(inputEmployee.getDepartment());
	}

	/**
	 * Test for deleteEmployee()
	 */
	@Test
	public void deleteEmployeeTest() {
		Mockito.when(employeeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(employee1));
		doNothing().when(employeeRepository).deleteById(Mockito.anyLong());
	}

	/**
	 * Test for updateEmployee()
	 */
	@Test
	public void updateEmployeeTest() {
		Mockito.when(employeeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(employee1));
		Mockito.when(employeeRepository.save(employee1)).thenReturn(employee1);
		EmployeeDTO updateEmployee = employeeService.updateEmployee(1L, inputEmployee);
		assertNotNull(updateEmployee);
		assertThat(updateEmployee.getId()).isEqualTo(1L);
		assertThat(updateEmployee.getName()).isEqualTo(inputEmployee.getName());
		assertThat(updateEmployee.getSalary()).isEqualTo(inputEmployee.getSalary());
		assertThat(updateEmployee.getDepartment()).isEqualTo(inputEmployee.getDepartment());
	}
}