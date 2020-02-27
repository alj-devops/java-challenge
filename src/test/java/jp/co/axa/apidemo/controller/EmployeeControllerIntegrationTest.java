package jp.co.axa.apidemo.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import jp.co.axa.apidemo.controllers.EmployeeController;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.services.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class)
public class EmployeeControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EmployeeService service;

    static byte[] toJson(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }

    @Test
    public void givenEmployees_whenGetEmployees_thenReturnJsonArray()
            throws Exception {

        Employee employee = new Employee( 10L, "TK", 12, "IT Dept");

        List<Employee> allEmployees = Arrays.asList(employee);

        given(service.retrieveEmployees()).willReturn(allEmployees);

        mvc.perform(get("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(employee.getName())));
    }

    @Test
    public void givenEmployeeId_whenGetEmployee_thenReturnEmployee()
            throws Exception {
        Long employeeId = 10L;
        Employee employee = new Employee(employeeId,"TK", 12, "IT Dept");

        given(service.getEmployee(employeeId)).willReturn(Optional.of(employee));

        mvc.perform(get("/api/v1/employees/" + employeeId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(employee.getName())));
    }

    @Test
    public void whenPostEmployee_thenCreateEmployee() throws Exception {
        Employee employee = new Employee("Thea", 12, "IT Dept");
        given(service.saveEmployee(Mockito.any())).willReturn(employee);

        mvc.perform(post("/api/v1/employees").contentType(MediaType.APPLICATION_JSON)
                .content(toJson(employee))).andExpect(status().isCreated());
        verify(service, VerificationModeFactory.times(1)).saveEmployee(Mockito.any());
        reset(service);
    }

    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenDeleteEmployee() throws Exception {
        Long employeeId = 10L;
        Employee employee = new Employee(employeeId,"Krista", 12, "IT Dept");

        given(service.getEmployee(employeeId)).willReturn(Optional.of(employee)).willReturn(null);

        mvc.perform(delete("/api/v1/employees/" + employeeId).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
        verify(service, VerificationModeFactory.times(1)).deleteEmployee(employeeId);
        reset(service);
    }

    @Test
    public void givenEmployeeId_whenPutEmployee_thenUpdateEmployee() throws Exception {
        Long employeeId = 10L;
        Employee employee = new Employee(employeeId,"Thea Krista", 12, "IT Dept");

        given(service.getEmployee(employeeId)).willReturn(Optional.of(employee));

        Employee updateDemployee = new Employee(employeeId,"Thea Krista", 1000000, "IT Dept");

        given(service.saveEmployee(updateDemployee)).willReturn(updateDemployee);

        mvc.perform(put("/api/v1/employees/").contentType(MediaType.APPLICATION_JSON)
                .content(toJson(updateDemployee))).andExpect(status().isOk())
                .andExpect(jsonPath("$.salary", is(updateDemployee.getSalary())));

        verify(service, VerificationModeFactory.times(1)).saveEmployee(updateDemployee);
        reset(service);
    }

}
