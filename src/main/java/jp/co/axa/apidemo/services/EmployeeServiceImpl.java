package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implements for handling the employeeService
 * 
 * @author AXA
 *
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

	/**
	 * Data handling Object
	 */
	@Autowired
	private EmployeeRepository employeeRepository;

	/**
	 * Setup the employeeRepository
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
			//print out Error log
			//For Prod, it is better to running the log4j
	        System.out.println("Employee retrive Error happened");
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
			//print out Error log
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
				//Avoid  to update the exist data
				//but it depends on the system request
				if (optEmp == null || optEmp.isEmpty()) {
					employeeRepository.save(employee);
			        System.out.println("Employee Saved Successfully");
			        return;
				}
			} else if(employee != null) {
				//update if the employeeid is empty
				employeeRepository.save(employee);
		        System.out.println("Employee Saved Successfully");
		        return;
			}
	        System.out.println("Employee Saved Failed");
		} catch (Exception e) {
			// print out Error log
	        System.out.println("Employee Saved Error happend");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void deleteEmployee(Long employeeId) {
		try {
			employeeRepository.deleteById(employeeId);
	        System.out.println("Employee Deleted Successfully");
		} catch (Exception e) {
	        System.out.println("Employee Deleted Error Happend");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void updateEmployee(Employee employee, Long employeeId) {
		try {
			// Checked the id should be the same for the target-id and id in the object
			if (employee != null && employeeId == employee.getId()) {
				Optional<Employee> optEmp = employeeRepository.findById(employeeId);
				//avoid save new data into the system
				//but it depend on the system request
				if (optEmp != null) {
					Employee emp = optEmp.get();
					if (emp != null) {
						employeeRepository.save(employee);
				        System.out.println("Employee update Successfully");
						return;
					}
				}
			}
	        System.out.println("Employee update failed");
		} catch (Exception e) {
	        System.out.println("Employee update error happend");		}
	}
}