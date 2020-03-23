package jp.co.axa.apidemo.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /**
     * Retrieves list of employees
     */
    public List<Employee> retrieveEmployees() {
        try {
            List<Employee> employees = employeeRepository.findAll();
            return employees;
        } catch (Exception e) {
            logger.error("Error while retrieving employees :(");
            logger.error("Message: " + e.getMessage());
            return null;
        }
    }

    /**
     * Retrieves an employee with a given id
     */
    public Employee getEmployee(Long employeeId) {
        try {
            if(employeeId != null) {
                Optional<Employee> optEmp = employeeRepository.findById(employeeId);
                return optEmp.get();
            } else {
                logger.warn("Please pass a valid employeeid");
                return null;
            }
        } catch (Exception e) {
            logger.error("Error while retrieving employee" + employeeId);
            logger.error("Message: " + e.getMessage());            
            return null;
        }
    }

    /**
     * Saves an employee
     */
    public void saveEmployee(Employee employee){
        try {
            if(employee == null) {
                logger.warn("Please pass a valid employee");
                return null;
            } else if(employee != null) {
                employeeRepository.save(employee);
                logger.info("Employee saved successfully");
            }
        } catch(Exception e) {
            logger.error("Error while saving employee");
            logger.error("Message: " + e.getMessage());            
            return null;
        }
    }

    /**
     * Deletes an employee with a given id
     */
    public void deleteEmployee(Long employeeId){
        try {
            if(employeeId == null) {
                logger.warn("Please pass a valid employee id");
                return null;
            } else if(employeeId != null) {
                employeeRepository.deleteById(employeeId);
                logger.info("Employee deleted successfully");
            }
        } catch(Exception e) {
            logger.error("Error while deleting employee");
            logger.error("Message: " + e.getMessage());            
            return null;
        }
    }

    /**
     * Updates an employee
     */
    public void updateEmployee(Employee employee) {
        try {
            if(employee == null) {
                logger.warn("Please pass a valid employee");
                return null;
            } else if(employee != null) {
                employeeRepository.save(employee);
                logger.info("Employee updated successfully");
            }
        } catch(Exception e) {
            logger.error("Error while updating employee");
            logger.error("Message: " + e.getMessage());            
            return null;
        }
    }
}