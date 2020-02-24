package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.dto.EmployeeDto;
import jp.co.axa.apidemo.entities.Employee;

import java.util.List;

/**
 * Interface of employee services
 */
public interface EmployeeService {

    public List<Employee> retrieveEmployees();

    public Long getEmployeeId(String name, String department);

    public EmployeeDto getEmployee(Long employeeId);

    public void createEmployee(EmployeeDto employee);

    public void deleteEmployee(Long employeeId);

    public void updateEmployee(Long employeeId, EmployeeDto employee);
}