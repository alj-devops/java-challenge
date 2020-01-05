package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.entities.Employee;

import java.util.List;

/**
 * Interface to handling the employee Information.
 *
 * @author AXA
 *
 */
public interface EmployeeService {

    /**
     * Get all the employee information.
     *
     * @return All the employees information
     */
    public abstract List<Employee> retrieveEmployees();

    /**
     * Get the employee information by employee Id.
     *
     * @param employeeId the targeting employee Id
     * @return the employee information
     */
    public abstract Employee getEmployee(Long employeeId);

    /**
     * Add new employee information without updating exist one.
     *
     * @param employee
     */
    public abstract void saveEmployee(Employee employee);

    /**
     * Delete the employee by employee Id.
     *
     * @param employeeId the targeting employee Id
     */
    public abstract void deleteEmployee(Long employeeId);

    /**
     * Update the Employee with exist ID.
     *
     * @param employee   the targeting employee object
     * @param employeeId the targeting id for employee
     */
    public abstract void updateEmployee(Employee employee, Long employeeId);
}
