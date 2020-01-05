package jp.co.axa.apidemo.controllers;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import javax.validation.Valid;

/**
 * Controller handling the Http request Access the API by /api/vi/apiName.
 *
 * @author Axa
 */
@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    /**
     * employeeService.
     */
    @Autowired
    private EmployeeService employeeService;

    /**
     * setup the employee service.
     *
     * @param employeeService for the initialization
     */
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * Get all the Employee information.
     *
     * @return All employees information List
     */
    @GetMapping("/employees")
    public List<Employee> getEmployees() {
        List<Employee> employees = employeeService.retrieveEmployees();
        return employees;
    }

    /**
     * Get the employee information by employee id.
     *
     * @param employeeId employee id
     * @return Employee information
     */
    @GetMapping("/employees/{employeeId}")
    public Employee getEmployee(@PathVariable(name = "employeeId") Long employeeId) {
        return employeeService.getEmployee(employeeId);
    }

    /**
     * Save the employee information into the DB and the Id will increased.
     *
     * @param employee the information we would like to save in the DB
     */
    @PostMapping("/employees")
    public void saveEmployee(@Valid @RequestBody Employee employee) {
        employeeService.saveEmployee(employee);
    }

    /**
     * Delete the employee information from DB by employee ID.
     *
     * @param employeeId employee Id
     */
    @DeleteMapping("/employees/{employeeId}")
    public void deleteEmployee(@PathVariable(name = "employeeId") Long employeeId) {
        employeeService.deleteEmployee(employeeId);
    }

    /**
     * Update the employee data by the Employee Id.
     *
     * @param employee   the updated employee information
     * @param employeeId the targeting employee Id
     */
    @PutMapping("/employees/{employeeId}")
    public void updateEmployee(@Valid @RequestBody Employee employee,
            @PathVariable(name = "employeeId") Long employeeId) {
        employeeService.updateEmployee(employee, employeeId);
    }

}
