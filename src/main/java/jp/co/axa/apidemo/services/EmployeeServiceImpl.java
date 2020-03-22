package jp.co.axa.apidemo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.exeption.EmployeeNotFoundException;
import jp.co.axa.apidemo.model.EmployeeDTO;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import jp.co.axa.apidemo.services.helper.EmployeeServiceHelper;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

	private static Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

	@Autowired
	private EmployeeServiceHelper employeeServiceHelper;

	@Autowired
	private EmployeeRepository employeeRepository;

	public void setEmployeeRepository(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	/**
	 * Retrieves all the employees
	 * 
	 * @return List of Employees
	 */
	public List<EmployeeDTO> retrieveEmployees() {
		// Fetch all emloyees from DB
		List<Employee> employees = employeeRepository.findAll();

		// Convert Employee Entity list into Employee Domain list
		List<EmployeeDTO> employeesList = new ArrayList<>();
		employees.stream().forEach(employee -> {
			EmployeeDTO emp = employeeServiceHelper.convertEmployeeEntityToEmployeeDomain(employee);
			employeesList.add(emp);
		});
		logger.debug("Found all employees:{}", employeesList);
		return employeesList;
	}

	/**
	 * Fetch a single employee record
	 * 
	 * @param employeeId Employee ID
	 * @return Employee record corresponding to employeeId or else throws
	 *         EmployeeNotFoundException exception
	 */
	public EmployeeDTO getEmployee(Long employeeId) {
		// Fetch the employee with @param employeeId
		Optional<Employee> employee = employeeRepository.findById(employeeId);
		employee.orElseThrow(() -> new EmployeeNotFoundException(employeeId));
		logger.debug("Found employee with ID:{}, Employee:{}", employeeId, employee);

		// Convert Employee Entity into Employee Domain
		return employeeServiceHelper.convertEmployeeEntityToEmployeeDomain(employee.get());
	}

	/**
	 * Save a new employee
	 * 
	 * @param EmployeeDTO new Employee information
	 * @return Saved Employee information
	 */
	public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
		// Convert Employee Domain to Employee Entity and perform save operation
		Employee employee = employeeServiceHelper.convertEmployeeDomainToEmployeeEntity(employeeDTO);

		// Convert Employee Entity to Employee Domain and Return the saved employee
		EmployeeDTO savedEmployee = employeeServiceHelper
				.convertEmployeeEntityToEmployeeDomain(employeeRepository.save(employee));
		logger.debug("Saved employee, Employee:{}", savedEmployee);
		return savedEmployee;
	}

	/**
	 * Delete an existing employee Throws EmployeeNotFoundException exception if
	 * employee to be deleted does not exists
	 * 
	 * @param employeeId Employee ID to be deleted
	 */
	public void deleteEmployee(Long employeeId) {
		// Check if employee with @param employeeId exists, if not throw
		// EmployeeNotFoundException exception
		if (getEmployee(employeeId) != null) {
			employeeRepository.deleteById(employeeId);
			logger.debug("Deleted employee with ID:{}", employeeId);
		}
	}

	/**
	 * Update an existing employee Throws EmployeeNotFoundException exception if
	 * employee to be updated does not exists
	 * 
	 * @param employeeId  Employee ID to be updated
	 * @param employeeDTO Employee information to be updated
	 * @return Updated employee information
	 */
	public EmployeeDTO updateEmployee(Long employeeId, EmployeeDTO employeeDTO) {
		// Check if employee with @param employeeId exists, if not throw
		// EmployeeNotFoundException exception
		Optional<Employee> employee = employeeRepository.findById(employeeId);
		employee.orElseThrow(() -> new EmployeeNotFoundException(employeeId));

		// Convert Employee Domain to Employee Entity and update the existing record
		Employee updateEmployee = employeeRepository.save(
				employeeServiceHelper.convertEmployeeDomainToEmployeeEntityForUpdate(employeeDTO, employee.get()));
		logger.debug("Updated employee, Employee:{}", updateEmployee);

		// Convert Employee Entity to Employee Domain and return the updated employee
		return employeeServiceHelper.convertEmployeeEntityToEmployeeDomain(updateEmployee);
	}
}