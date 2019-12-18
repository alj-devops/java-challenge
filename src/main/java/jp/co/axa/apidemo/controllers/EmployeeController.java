package jp.co.axa.apidemo.controllers;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 
 * Controller handling the Http request
 * Access the API by localhost:8080/api/vi/apiName
 * @author Axa
 *
 */
@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

	/**
	 * employeeService 
	 */
    @Autowired
    private EmployeeService employeeService;

    /**
     * setup the employee service 
     * @param employeeService for the initialization
     */
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    
    /**
     * Get all the Employee information
     * @return All employees information List 
     */
    @GetMapping("/employees")
    public List<Employee> getEmployees() {
        List<Employee> employees = employeeService.retrieveEmployees();
        return employees;
    }

    /**
     * Get the employee information by employee id 
     * @param employeeId employee id  
     * @return Employee information
     */
    @GetMapping("/employees/{employeeId}")
    public Employee getEmployee(@PathVariable(name="employeeId")Long employeeId) {
        return employeeService.getEmployee(employeeId);
    }

    /**
     * Save the employee information into the DB and the Id will increased
     * @param employee the information we would like to save in the DB
     */
    @PostMapping("/employees")
    public void saveEmployee(@RequestBody Employee employee){
        employeeService.saveEmployee(employee);
    }
    
    /**
     * Delete the emplyee information from DB by employee ID
     * @param employeeId empleyee Id
     */
    @DeleteMapping("/employees/{employeeId}")
    public void deleteEmployee(@PathVariable(name="employeeId")Long employeeId){
        employeeService.deleteEmployee(employeeId);
    }

    /**
     * Update the employee data by the Employee Id     
     * @param employee the updated employee information
     * @param employeeId the targeting employee Id
     */
    @PutMapping("/employees/{employeeId}")
    public void updateEmployee(@RequestBody Employee employee,
                               @PathVariable(name="employeeId")Long employeeId){
    	//Because of the change of the update function,the logic has been moved into service
    	employeeService.updateEmployee(employee, employeeId);
    }

}
