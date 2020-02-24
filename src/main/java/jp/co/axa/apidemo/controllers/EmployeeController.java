package jp.co.axa.apidemo.controllers;

import jp.co.axa.apidemo.dto.EmployeeDto;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller to serve REST API endpoints
 */
@RestController
@RequestMapping("/api/v1/employees/")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // except for getAll, none of the other methods shall return id of employee as it is an internal information
    // getAll needs to return id as well so that it can be used for update and delete operations

    /**
     * Get information of all employees
     * @return List of employees including their private ids
     */
    @GetMapping("getAll")
    public List<Employee> getEmployees() {
        return employeeService.retrieveEmployees();
    }

    /**
     * Get information of given employee id
     * @param employeeId employee Id
     * @return Employee public information
     */
    @GetMapping("{employeeId}")
    public EmployeeDto getEmployee(@PathVariable(name = "employeeId") Long employeeId) {
        return employeeService.getEmployee(employeeId);
    }

    /**
     * Create a new employee
     * @param employeeDto employee public information
     */
    @PostMapping("create")
    public void createEmployee(@Valid EmployeeDto employeeDto) {
        employeeService.createEmployee(employeeDto);
    }

    /**
     * Update an existing employee
     * @param employeeDto employee's new public information
     * @param employeeId existing employee id
     */
    @PutMapping("update/{employeeId}")
    public void updateEmployee(@RequestBody @Valid EmployeeDto employeeDto,
                               @PathVariable(name = "employeeId") Long employeeId) {
        employeeService.updateEmployee(employeeId, employeeDto);
    }

    /**
     * Delete an existing employee
     * @param employeeId existing employee id
     */
    @DeleteMapping("delete/{employeeId}")
    public void deleteEmployee(@PathVariable(name = "employeeId") Long employeeId) {
        employeeService.deleteEmployee(employeeId);
    }
}
