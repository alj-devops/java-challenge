package jp.co.axa.apidemo.services;

import org.junit.Before;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import jp.co.axa.apidemo.entities.Employee;

/**
 * Test for the employeeService. For the parameter check it should be done in
 * Controller Test.
 *
 * @author li.han
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class EmployeeServiceImplTest {
    /**
     * EmployeeService for test.
     */
    @Autowired
    private EmployeeService employeeService;

    /**
     * employee1 with Department, Name, Salary.
     */
    private Employee employee1 = new Employee();

    /**
     * employee2 with Department, Name, Salary, different id.
     */
    private Employee employee2 = new Employee();

    /**
     * employee with same id.
     */
    private Employee employeeWithSameId = new Employee();

    /**
     * employee1 salary.
     */
    private final int employee1Salary = 1000;

    /**
     * employee1 salary for update test.
     */
    private final int updateEmployee1Salary1 = 1001;

    /**
     * another employee1 salary for update test.
     */
    private final int updateEmployee1Salary2 = 1001;

    /**
     * employee2 salary.
     */
    private final int employee2Salary = 2000;

    /**
     * employee2 id.
     */
    private final Long employee2Id = 2001L;

    /**
     * employee3 salary.
     */
    private final int employee3Salary = 3000;

    /**
     * Initial the the test data.
     */
    @Before
    public void setup() {
        // Employee without Id
        employee1.setDepartment("testDep1");
        employee1.setName("testUser1");
        employee1.setSalary(employee1Salary);

        // Employee with Id
        employee2.setDepartment("testDep2");
        employee2.setId(employee2Id);
        employee2.setName("testUser2");
        employee2.setSalary(employee2Salary);

        // Employee With Same Id, the id will be update in test
        employeeWithSameId.setDepartment("testDep3");
        employeeWithSameId.setName("testUser3");
        employeeWithSameId.setSalary(employee3Salary);
    }

    /**
     * Test for set new employee information.
     */
    @Test
    public void testSaveEmployee() {
        // Normal Cases
        employeeService.saveEmployee(employee1);
        Long id = employee1.getId();
        Employee employeeGet = employeeService.getEmployee(id);
        Assert.assertEquals("testDep1", employeeGet.getDepartment());
        Assert.assertEquals("testUser1", employeeGet.getName());
        Assert.assertEquals(Integer.valueOf(employee1Salary), employeeGet.getSalary());

        // Normal Cases while employee with Id not exist
        id += 1;
        employeeService.saveEmployee(employee2);
        employeeGet = employeeService.getEmployee(id);
        Assert.assertEquals("testDep2", employeeGet.getDepartment());
        Assert.assertEquals("testUser2", employeeGet.getName());
        Assert.assertNotEquals(employee2Id, id);
        Assert.assertEquals(id, employeeGet.getId());
        Assert.assertEquals(Integer.valueOf(employee2Salary), employeeGet.getSalary());

        // abnormal Cases with same id
        employeeWithSameId.setId(id);
        employeeService.saveEmployee(employeeWithSameId);
        employeeGet = employeeService.getEmployee(id);
        Assert.assertEquals("testDep2", employeeGet.getDepartment());
        Assert.assertEquals("testUser2", employeeGet.getName());
        Assert.assertEquals(id, employeeGet.getId());
        Assert.assertEquals(Integer.valueOf(employee2Salary), employeeGet.getSalary());
        id += 1;
        employeeGet = employeeService.getEmployee(id);
        Assert.assertNull(employeeGet);

        // abnormal Cases with null object
        employeeService.saveEmployee(null);
        employeeGet = employeeService.getEmployee(id);
        Assert.assertNull(employeeGet);
    }

    /**
     * Test to get all the employees list.
     */
    @Test
    public void testRetrieveEmployees() {
        // Normal cases with 0 items
        List<Employee> result = employeeService.retrieveEmployees();
        Assert.assertEquals(0, result.size());

        // Normal cases with 1 items
        employeeService.saveEmployee(employee1);
        Long id1 = employee1.getId();
        result = employeeService.retrieveEmployees();
        Assert.assertEquals(1, result.size());
        Assert.assertEquals("testDep1", result.get(0).getDepartment());
        Assert.assertEquals("testUser1", result.get(0).getName());
        Assert.assertEquals(id1, result.get(0).getId());
        Assert.assertEquals(Integer.valueOf(employee1Salary), result.get(0).getSalary());

        // Normal cases with 2 items
        employeeService.saveEmployee(employee2);
        Long id2 = id1 + 1;
        result = employeeService.retrieveEmployees();
        Assert.assertEquals(2, result.size());
        Assert.assertEquals("testDep1", result.get(0).getDepartment());
        Assert.assertEquals("testUser1", result.get(0).getName());
        Assert.assertEquals(id1, result.get(0).getId());
        Assert.assertEquals(Integer.valueOf(employee1Salary), result.get(0).getSalary());
        Assert.assertEquals("testDep2", result.get(1).getDepartment());
        Assert.assertEquals("testUser2", result.get(1).getName());
        Assert.assertEquals(id2, result.get(1).getId());
        Assert.assertEquals(Integer.valueOf(employee2Salary), result.get(1).getSalary());
    }

    /**
     * Test for get employeeId by Id.
     */
    @Test
    public void testGetEmployee() {
        // Normal cases
        employeeService.saveEmployee(employee1);
        Long id = employee1.getId();
        Employee employeeGet = employeeService.getEmployee(id);
        Assert.assertEquals("testDep1", employeeGet.getDepartment());
        Assert.assertEquals("testUser1", employeeGet.getName());
        Assert.assertEquals(Integer.valueOf(employee1Salary), employeeGet.getSalary());

        // Abnormal cases get not exist id
        employeeGet = employeeService.getEmployee(id + 1);
        Assert.assertNull(employeeGet);

        // Abnormal cases get not exist id(id=0)
        employeeGet = employeeService.getEmployee(0L);
        Assert.assertNull(employeeGet);

        // Abnormal cases get not exist id(id<0)
        employeeGet = employeeService.getEmployee(-1L);
        Assert.assertNull(employeeGet);

        // Abnormal cases with null cases
        employeeGet = employeeService.getEmployee(null);
        Assert.assertNull(employeeGet);
    }

    /**
     * Test for update the exist employee.
     */
    @Test
    public void testUpdateEmployee() {
        employeeService.saveEmployee(employee1);
        Long id = employee1.getId();
        List<Employee> result = employeeService.retrieveEmployees();
        Assert.assertEquals(1, result.size());
        Employee employeeUpdate = new Employee();

        // Normal Cases
        employeeUpdate.setDepartment("testDep1Update1");
        employeeUpdate.setName("testUser1Update1");
        employeeUpdate.setSalary(updateEmployee1Salary1);
        employeeUpdate.setId(id);
        employeeService.updateEmployee(employeeUpdate, id);
        Employee employeeGet = employeeService.getEmployee(id);
        Assert.assertEquals("testDep1Update1", employeeGet.getDepartment());
        Assert.assertEquals("testUser1Update1", employeeGet.getName());
        Assert.assertEquals(Integer.valueOf(updateEmployee1Salary1), employeeGet.getSalary());

        // abnormal Cases without id in employee
        employeeUpdate.setDepartment("testDep1Update2");
        employeeUpdate.setName("testUser1Update2");
        employeeUpdate.setSalary(updateEmployee1Salary2);
        employeeUpdate.setId(null);
        employeeService.updateEmployee(employeeUpdate, id);
        employeeGet = employeeService.getEmployee(id);
        Assert.assertEquals("testDep1Update1", employeeGet.getDepartment());
        Assert.assertEquals("testUser1Update1", employeeGet.getName());
        Assert.assertEquals(Integer.valueOf(updateEmployee1Salary1), employeeGet.getSalary());

        // abnormal Cases without targeting id(id=null)
        employeeUpdate.setDepartment("testDep1Update2");
        employeeUpdate.setName("testUser1Update2");
        employeeUpdate.setSalary(updateEmployee1Salary2);
        employeeUpdate.setId(id);
        employeeService.updateEmployee(employeeUpdate, null);
        employeeGet = employeeService.getEmployee(id);
        Assert.assertEquals("testDep1Update1", employeeGet.getDepartment());
        Assert.assertEquals("testUser1Update1", employeeGet.getName());
        Assert.assertEquals(Integer.valueOf(updateEmployee1Salary1), employeeGet.getSalary());

        // abnormal Cases targeting id is different
        employeeUpdate.setDepartment("testDep1Update2");
        employeeUpdate.setName("testUser1Update2");
        employeeUpdate.setSalary(updateEmployee1Salary2);
        employeeUpdate.setId(id);
        employeeService.updateEmployee(employeeUpdate, id + 1);
        employeeGet = employeeService.getEmployee(id);
        Assert.assertEquals("testDep1Update1", employeeGet.getDepartment());
        Assert.assertEquals("testUser1Update1", employeeGet.getName());
        Assert.assertEquals(Integer.valueOf(updateEmployee1Salary1), employeeGet.getSalary());
        employeeGet = employeeService.getEmployee(id + 1);
        Assert.assertNull(employeeGet);

        // abnormal Cases targeting id is not exist
        employeeUpdate.setDepartment("testDep1Update2");
        employeeUpdate.setName("testUser1Update2");
        employeeUpdate.setSalary(updateEmployee1Salary2);
        employeeUpdate.setId(id + 1);
        employeeService.updateEmployee(employeeUpdate, id + 1);
        employeeGet = employeeService.getEmployee(id);
        Assert.assertEquals("testDep1Update1", employeeGet.getDepartment());
        Assert.assertEquals("testUser1Update1", employeeGet.getName());
        Assert.assertEquals(Integer.valueOf(updateEmployee1Salary1), employeeGet.getSalary());
        employeeGet = employeeService.getEmployee(id + 1);
        Assert.assertNull(employeeGet);
    }

    /**
     * Test for deleting the employee.
     */
    @Test
    public void testDeleteEmployee() {
        employeeService.saveEmployee(employee1);
        employeeService.saveEmployee(employee2);
        Long id = employee1.getId();
        List<Employee> result = employeeService.retrieveEmployees();
        Assert.assertEquals(2, result.size());

        // Normal cases
        employeeService.deleteEmployee(id + 1);
        result = employeeService.retrieveEmployees();
        Assert.assertEquals(1, result.size());
        Assert.assertEquals("testDep1", result.get(0).getDepartment());
        Assert.assertEquals("testUser1", result.get(0).getName());
        Assert.assertEquals(id, result.get(0).getId());
        Assert.assertEquals(Integer.valueOf(employee1Salary), result.get(0).getSalary());
        Employee employeeGet = employeeService.getEmployee(id + 1);
        Assert.assertNull(employeeGet);

        // Abnormal cases get not exist id
        employeeService.deleteEmployee(id + 1);
        result = employeeService.retrieveEmployees();
        Assert.assertEquals(1, result.size());
        Assert.assertEquals("testDep1", result.get(0).getDepartment());
        Assert.assertEquals("testUser1", result.get(0).getName());
        Assert.assertEquals(id, result.get(0).getId());
        Assert.assertEquals(Integer.valueOf(employee1Salary), result.get(0).getSalary());

        // Abnormal cases get not exist id(id=0)
        employeeService.deleteEmployee(0L);
        result = employeeService.retrieveEmployees();
        Assert.assertEquals(1, result.size());
        Assert.assertEquals("testDep1", result.get(0).getDepartment());
        Assert.assertEquals("testUser1", result.get(0).getName());
        Assert.assertEquals(id, result.get(0).getId());
        Assert.assertEquals(Integer.valueOf(employee1Salary), result.get(0).getSalary());

        // Abnormal cases get not exist id(id<0)
        employeeService.deleteEmployee(-1L);
        result = employeeService.retrieveEmployees();
        Assert.assertEquals(1, result.size());
        Assert.assertEquals("testDep1", result.get(0).getDepartment());
        Assert.assertEquals("testUser1", result.get(0).getName());
        Assert.assertEquals(id, result.get(0).getId());
        Assert.assertEquals(Integer.valueOf(employee1Salary), result.get(0).getSalary());

        // Abnormal cases with null object)
        employeeService.deleteEmployee(null);
        result = employeeService.retrieveEmployees();
        Assert.assertEquals(1, result.size());
        Assert.assertEquals("testDep1", result.get(0).getDepartment());
        Assert.assertEquals("testUser1", result.get(0).getName());
        Assert.assertEquals(id, result.get(0).getId());
        Assert.assertEquals(Integer.valueOf(employee1Salary), result.get(0).getSalary());
    }

}
