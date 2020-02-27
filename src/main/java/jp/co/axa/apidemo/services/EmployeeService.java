package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.entities.Employee;

import java.util.List;

public interface EmployeeService {
    
    void deleteEmployee(Long employeeId);
    
    Employee getEmployee(Long employeeId);
    
    List<Employee> retrieveEmployees();
    
    void saveEmployee(Employee employee);
    
    void updateEmployee(Employee employee);
}