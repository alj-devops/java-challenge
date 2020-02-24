package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.converter.EmployeeConverter;
import jp.co.axa.apidemo.dto.EmployeeDto;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of EmployeeService interface
 */
@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeConverter employeeConverter;

    public List<Employee> retrieveEmployees() {
        return employeeRepository.findAll();
    }

    public Long getEmployeeId(String name, String department) {
        return employeeRepository.findId(name, department);
    }

    public EmployeeDto getEmployee(Long employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if (employee.isPresent()) {
            log.info("Employee information fetched successfully");
            return employeeConverter.convert(employee.get());
        } else
            throw new RuntimeException("Employee id does not exist");
    }

    public void createEmployee(EmployeeDto employeeDto) {
        Long id = employeeRepository.findId(employeeDto.getName(), employeeDto.getDepartment());
        if (id == null) {
            employeeRepository.save(employeeConverter.convert(employeeDto));
            log.info("Employee information created successfully");
        } else
            throw new RuntimeException("Employee with same name and department already exists");
    }

    public void deleteEmployee(Long employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if (employee.isPresent()) {
            employeeRepository.deleteById(employeeId);
            log.info("Employee information deleted successfully");
        } else
            throw new RuntimeException("Employee id does not exist");
    }

    public void updateEmployee(Long employeeId, EmployeeDto employeeDto) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if (employee.isPresent()) {
            employeeRepository.save(employeeConverter.combine(employeeId, employeeDto));
            log.info("Employee information updated successfully");
        } else
            throw new RuntimeException("Employee id does not exist");
    }
}