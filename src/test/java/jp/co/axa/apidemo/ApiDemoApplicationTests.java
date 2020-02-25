package jp.co.axa.apidemo;

import java.util.ArrayList;
import java.util.List;

import jp.co.axa.apidemo.controllers.EmployeeController;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.entities.EmployeeDto;
import jp.co.axa.apidemo.services.EmployeeService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
public class ApiDemoApplicationTests {

    @InjectMocks
    private EmployeeController employeeController;
    @Mock
    EmployeeService employeeService;

    @Test
    public void testGetAll() {
        Employee e1 = new Employee(1L, "A", 1000, "dep1");
        Employee e2 = new Employee(2L, "B", 2000, "dep2");
        Employee e3 = new Employee(3L, "C", 3000, "dep3");
        List<Employee> list = new ArrayList<>();
        list.add(e1);
        list.add(e2);
        list.add(e3);

        when(employeeService.retrieveEmployees()).thenReturn(list);
        List<Employee> result = employeeController.getEmployees();
        assertEquals(result.size(), 3);
        assertEquals(result.get(0).getDepartment(), "dep1");
        assertEquals(result.get(1).getSalary(), (Integer)2000);
        assertEquals(result.get(2).getName(), "C");
        assertEquals(result.get(2).getId(), (Long)3L);
   }

   @Test
   public void testGetEmployee() {
       Employee e1 = new Employee(1L, "A", 1000, "dep1");
       Employee e2 = new Employee(2L, "B", 2000, "dep2");
       when(employeeService.getEmployee(1L)).thenReturn(e1);
       when(employeeService.getEmployee(2L)).thenReturn(e2);
       when(employeeService.getEmployee(3L)).thenReturn(null);

       ResponseEntity<Employee> result1 = employeeController.getEmployee(1L);
       assertEquals(result1.getStatusCode(), HttpStatus.OK);
       assertEquals(result1.getBody().getId(), (Long)1L);
       assertEquals(result1.getBody().getName(), "A");
       assertEquals(result1.getBody().getSalary(), (Integer)1000);

       ResponseEntity<Employee> result2 = employeeController.getEmployee(2L);
       assertEquals(result2.getBody().getId(), (Long)2L);
       assertEquals(result2.getBody().getName(), "B");
       assertEquals(result2.getBody().getSalary(), (Integer)2000);

       ResponseEntity<Employee> result3 = employeeController.getEmployee(3L);
       assertEquals(result3.getStatusCode(), HttpStatus.NOT_FOUND);
   }

   @Test
   public void testPutStatuses() {
       Employee e1 = new Employee(1L, "A", 1000, "dep1");
       when(employeeService.getEmployee(1L)).thenReturn(e1);

       HttpStatus status1 = employeeController.updateEmployee(new EmployeeDto(), 1L);
       HttpStatus status2 = employeeController.updateEmployee(new EmployeeDto(), 2L);
       assertEquals(status1, HttpStatus.OK);
       assertEquals(status2, HttpStatus.NOT_FOUND);
   }

    @Test
    public void testDeleteStatuses() {
        doNothing().when(employeeService).deleteEmployee(1L);
        doThrow(EmptyResultDataAccessException.class).when(employeeService).deleteEmployee(2L);

        HttpStatus status1 = employeeController.deleteEmployee(1L);
        HttpStatus status2 = employeeController.deleteEmployee(2L);
        assertEquals(status1, HttpStatus.OK);
        assertEquals(status2, HttpStatus.NOT_FOUND);
    }

}
