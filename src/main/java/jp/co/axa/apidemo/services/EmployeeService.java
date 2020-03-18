package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.dto.response.EmployeesResponseDto;
import jp.co.axa.apidemo.dto.request.EmployeeDto;
import jp.co.axa.apidemo.dto.request.PatchUpdateEmployeeDto;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.exceptions.EmployeeNotFoundException;

public interface EmployeeService {

    /**
     * Returns a list of employees within a certain size.
     * @param page from what page we should begin. This acts as the offset
     * @param size how many employees to retrieve
     * @return EmployeesResponseDto
     */
    EmployeesResponseDto retrieveEmployees(int page, int size);

    /**
     * Get a single employee given an employee id. If not found, EmployeeNotFoundException
     * @param employeeId Long
     * @return Employee
     * @throws EmployeeNotFoundException
     */
    Employee getEmployee(Long employeeId) throws EmployeeNotFoundException;

    /**
     * Creates a new employee
     * @param createEmployeeDto EmployeeDto
     * @return Employee
     */
    Employee saveEmployee(EmployeeDto createEmployeeDto);

    /**
     * Deletes a single employee given an id. If not found, EmployeeNotFoundException is thrown.
     * @param employeeId Long
     * @throws EmployeeNotFoundException
     */
    void deleteEmployee(Long employeeId) throws EmployeeNotFoundException;

    /**
     * Updates all fields of a single employee given an id. If not found, EmployeeNotFoundException is thrown.
     * @param employeeId Long
     * @throws EmployeeNotFoundException
     */
    void updateEmployee(Long employeeId, EmployeeDto employee) throws EmployeeNotFoundException;

    /**
     * Parches update single fields in an employee.
     * @param employeeId
     * @param employee
     * @throws EmployeeNotFoundException
     */
    void patchUpdateEmployee(Long employeeId, PatchUpdateEmployeeDto employee) throws EmployeeNotFoundException;
}