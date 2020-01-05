package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.repositories.EmployeeRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implements for handling the employeeService.
 *
 * @author AXA
 *
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    /**
     * Data handling Object.
     */
    @Autowired
    private EmployeeRepository employeeRepository;
    /**
     * logger.
     */
    private static Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    /**
     * Setup the employeeRepository.
     *
     * @param employeeRepository
     */
    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /**
     * {@inheritDoc}
     */
    public List<Employee> retrieveEmployees() {
        try {
            List<Employee> employees = employeeRepository.findAll();
            return employees;
        } catch (Exception e) {
            logger.error("Employee retrive Error happened:" + e.getMessage());
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    public Employee getEmployee(Long employeeId) {
        try {
            Optional<Employee> optEmp = employeeRepository.findById(employeeId);
            return optEmp.get();
        } catch (Exception e) {
            logger.error("Employee get Error happened:" + e.getMessage());
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    public void saveEmployee(Employee employee) {
        try {
            if (employee != null && employee.getId() != null) {
                Optional<Employee> optEmp = employeeRepository.findById(employee.getId());
                if (optEmp == null || optEmp.isEmpty()) {
                    employeeRepository.save(employee);
                    logger.info("Employee Saved Successfully");
                    return;
                } else {
                    logger.warn("Employee Saved Failed");
                    logger.warn("We could not save a employee infomation in an exist one");
                    return;
                }
            } else if (employee != null) {
                employeeRepository.save(employee);
                logger.info("Employee Saved Successfully");
                return;
            }
            logger.warn("Employee Saved Failed");
        } catch (Exception e) {
            logger.error("Employee Saved Error happend:" + e.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    public void deleteEmployee(Long employeeId) {
        try {
            employeeRepository.deleteById(employeeId);
            logger.info("Employee Deleted Successfully");
        } catch (Exception e) {
            logger.error("Employee Deleted Error Happend:" + e.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    public void updateEmployee(Employee employee, Long employeeId) {
        try {
            if (employee != null && employeeId == employee.getId()) {
                Optional<Employee> optEmp = employeeRepository.findById(employeeId);
                if (optEmp != null) {
                    Employee emp = optEmp.get();
                    if (emp != null) {
                        employeeRepository.save(employee);
                        logger.info("Employee update Successfully");
                        return;
                    }
                }
            }
            logger.warn("Employee uodate failed because of dismatched data");
        } catch (Exception e) {
            logger.error("Employee update error happend:" + e.getMessage());
        }
    }
}
