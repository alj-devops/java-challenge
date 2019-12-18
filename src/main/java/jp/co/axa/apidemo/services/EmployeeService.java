package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.entities.Employee;

import java.util.List;

/**
 * Interface to handling the employee Information 
 * @author AXA
 *
 */
public interface EmployeeService {

    /**
     * Get all the employee information 
     * @return All the employees information 
     */
	public List<Employee> retrieveEmployees();
	
    /**
     * Get the employee information by employee Id 
     * @param employeeId the targeting employee Id
     * @return the employee information 
     */
    public Employee getEmployee(Long employeeId);

    /**
     * Add new employee information 
     * If the employee is exist, it will update the employee information
     * @param employee
     */
    public void saveEmployee(Employee employee);

    /** 
     * Delete the employee by employee Id
     * @param employeeId the targeting employee Id
     */
    public void deleteEmployee(Long employeeId);

    /**
     * Update the Employee
     * Modified the function to make sure the  update targeting data has been updated rather than to be added as new
     * @param employee the targeting employee object
     */
    public void updateEmployee(Employee employee, Long employeeId);
}