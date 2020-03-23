package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.entities.Employee;

import java.util.List;

/**
 * Interface to handle all employee CRUDs
 *
 */

public interface EmployeeService {

    /**
     *
     * @return All the employees information
     */
    public List<Employee> retrieveEmployees();

    /**
     * @param employeeId
     * @return Information of a particular employee
     */
    public Employee getEmployee(Long employeeId);

    /**
     * Saves a new Employee to the system
     * @param employee
     */
    public void saveEmployee(Employee employee);

    /**
     * Deletes a given employee from the system
     * @param employeeId
     */
    public void deleteEmployee(Long employeeId);

    /**
     * Updates the information of an employee in the sysyem
     * @param employee
     */
    public void updateEmployee(Employee employee);
}