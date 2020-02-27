package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for employee CRUD operations
 *
 * @author Thea Krista
 * @version 1.0
 * @since api-demo-0.0.1
 */
@Service
@RequiredArgsConstructor
public class EmployeeService {
    /**
     * Employee repository to fetch/persist employee
     */
    private final EmployeeRepository employeeRepository;

    /**
     * List all employee data
     *
     * @return list of employees
     */
    public List<Employee> retrieveEmployees() {
        return employeeRepository.findAll();
    }

    /**
     * Get employee data
     *
     * @param employeeId - the employee id to retrieve
     * @return employee data
     */
    public Optional<Employee> getEmployee(Long employeeId) {
        return employeeRepository.findById(employeeId);
    }

    /**
     * Save employee data
     *
     * @param employee to save
     * @return saved employee data
     */
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    /**
     * Deletes employee data
     *
     * @param employeeId - the employee id to delete
     */
    public void deleteEmployee(Long employeeId) {
        employeeRepository.deleteById(employeeId);
    }

}