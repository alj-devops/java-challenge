package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.repositories.EmployeeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jp.co.axa.apidemo.errors.*;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    private static Logger log = LogManager.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> retrieveEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees;
    }

    public Employee getEmployee(Long employeeId) {
        return employeeRepository.findById(employeeId)
        .orElseThrow(() -> new EmployeeNotFoundException(employeeId));
        
    }

    public Employee saveEmployee(Employee employee){
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long employeeId){
        employeeRepository.deleteById(employeeId);
    }

    public Employee updateEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Employee employee, Long employeeId) {

        Optional<Employee> optEmp = employeeRepository.findById(employeeId);

        if(!optEmp.isPresent()){
            log.error("Employee Not Found Id : = " +employeeId);
            throw new EmployeeNotFoundException(employeeId);
        }
        
        Employee emp = optEmp.get();

        emp.setName(employee.getName());
        emp.setSalary(employee.getSalary());
        emp.setDepartment(employee.getDepartment());

       return employeeRepository.save(emp);
        
    }
}