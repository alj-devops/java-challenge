package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.entities.Employee;

import java.util.List;

public interface EmployeeService {
    
    void deleteEmployee(final Long employeeId);
    
    Employee getEmployee(final Long employeeId);
    
    List<Employee> retrieveEmployees();
    
    void saveEmployee(final Employee employee);
    
    void updateEmployee(final Employee employee);
}