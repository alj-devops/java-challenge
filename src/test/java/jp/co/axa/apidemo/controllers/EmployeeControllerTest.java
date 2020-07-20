package jp.co.axa.apidemo.controllers;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.services.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

	@MockBean EmployeeService employeeService;

	@Autowired private MockMvc mockMvc;

	@Before public void init() {

	}

	@Test
	public void shouldReturAllEmployees() throws Exception {
		when(employeeService.retrieveEmployees()).thenReturn(
				Arrays.asList(new Employee(1L, "employee_1", 10000, "department_1"),
						new Employee(2L, "employee_2", 20000, "department_2")));

		mockMvc.perform(get("/api/v1/employees")).andExpect(status().isOk())
				.andExpect(jsonPath("$.[0].id").value(1))
				.andExpect(jsonPath("$.[0].name").value("employee_1"))
				.andExpect(jsonPath("$.[0].salary").value(10000))
				.andExpect(jsonPath("$.[0].department").value("department_1"))
				.andExpect(jsonPath("$.[1].id").value(2))
				.andExpect(jsonPath("$.[1].name").value("employee_2"))
				.andExpect(jsonPath("$.[1].salary").value(20000))
				.andExpect(jsonPath("$.[1].department").value("department_2"));
	}

	@Test
	public void shouldReturRequestedEmployee() throws Exception {
		final Long employeeId = 1L;
		final Employee employee = new Employee(1L, "employee_1", 10000, "department_1");
		when(employeeService.getEmployee(employeeId)).thenReturn(Optional.of(employee));

		mockMvc.perform(get("/api/v1/employees/{employeeId}", employeeId)).andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.department").value("department_1"))
				.andExpect(jsonPath("$.name").value("employee_1"))
				.andExpect(jsonPath("$.salary").value(10000));
	}

	@Test
	public void shouldNotReturnEmployee() throws Exception {

		when(employeeService.getEmployee(anyLong())).thenReturn(Optional.empty());
		this.mockMvc.perform(get("/api/v1/employees/1"))
				.andDo(print()).andExpect(status().isNotFound());
	}


}
