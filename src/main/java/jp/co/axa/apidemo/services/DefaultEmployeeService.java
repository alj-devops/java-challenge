package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.exceptions.EmployeeNotFoundException;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultEmployeeService implements EmployeeService {
    
    private final EmployeeRepository employeeRepository;
    
    @Autowired
    public DefaultEmployeeService(final EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }
    
    public void deleteEmployee(final Long employeeId) {
        employeeRepository.deleteById(employeeId);
    }
    
    public Employee getEmployee(final Long employeeId) {
        
        return employeeRepository.findById(employeeId)
                                 .orElseThrow(EmployeeNotFoundException::new);
    }
    
    public List<Employee> retrieveEmployees() {
        return employeeRepository.findAll();
    }
    
    public void saveEmployee(final Employee employee) {
        employeeRepository.save(employee);
    }
    
    public void updateEmployee(final Employee employee) {
        employeeRepository.save(employee);
    }
}