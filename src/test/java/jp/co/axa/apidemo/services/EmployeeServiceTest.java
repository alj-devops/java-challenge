package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.ApiDemoApplicationTests;
import jp.co.axa.apidemo.dto.EmployeeDto;
import jp.co.axa.apidemo.entities.Employee;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Integration test of backend and DB
 */
@Transactional
public class EmployeeServiceTest extends ApiDemoApplicationTests {
    @Autowired
    private EmployeeService employeeService;

    @Test
    public void createAndRetrieveEmployees() {
        EmployeeDto employee1 = EmployeeDto.Builder.employeeDto()
                .withName("Emp1")
                .withSalary(10000)
                .withDepartment("Sales")
                .build();
        EmployeeDto employee2 = EmployeeDto.Builder.employeeDto()
                .withName("Emp2")
                .withSalary(20000)
                .withDepartment("Technology")
                .build();
        employeeService.createEmployee(employee1);
        employeeService.createEmployee(employee2);
        List<Employee> employeeList = employeeService.retrieveEmployees();
        Assert.assertEquals(2, employeeList.size());
    }

    @Test
    public void getEmployee() {
        EmployeeDto employee1 = EmployeeDto.Builder.employeeDto()
                .withName("Emp1")
                .withSalary(10000)
                .withDepartment("Sales")
                .build();
        employeeService.createEmployee(employee1);
        Long employeeId = employeeService.getEmployeeId(employee1.getName(), employee1.getDepartment());
        EmployeeDto employeeDto = employeeService.getEmployee(employeeId);
        Assert.assertEquals("Emp1", employeeDto.getName());
    }

    @Test
    public void deleteEmployee() {
        EmployeeDto employee1 = EmployeeDto.Builder.employeeDto()
                .withName("Emp1")
                .withSalary(10000)
                .withDepartment("Sales")
                .build();
        employeeService.createEmployee(employee1);
        Long employeeId = employeeService.getEmployeeId(employee1.getName(), employee1.getDepartment());
        EmployeeDto employeeDto = employeeService.getEmployee(employeeId);
        Assert.assertEquals("Emp1", employeeDto.getName());
        employeeService.deleteEmployee(employeeId);
        List<Employee> employeeList = employeeService.retrieveEmployees();
        Assert.assertEquals(0, employeeList.size());
    }

    @Test
    public void updateEmployee() {
        EmployeeDto employee1 = EmployeeDto.Builder.employeeDto()
                .withName("Emp1")
                .withSalary(10000)
                .withDepartment("Sales")
                .build();
        employeeService.createEmployee(employee1);
        Long employeeId = employeeService.getEmployeeId(employee1.getName(), employee1.getDepartment());
        EmployeeDto employeeDto = employeeService.getEmployee(employeeId);
        Assert.assertEquals("Emp1", employeeDto.getName());
        Assert.assertEquals(10000, employeeDto.getSalary().intValue());
        employee1.setSalary(15000);
        employeeService.updateEmployee(employeeId, employee1);
        employeeDto = employeeService.getEmployee(employeeId);
        Assert.assertEquals("Emp1", employeeDto.getName());
        Assert.assertEquals(15000, employeeDto.getSalary().intValue());
    }
}