package jp.co.axa.apidemo.controllers;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.axa.apidemo.model.EmployeeDTO;
import jp.co.axa.apidemo.services.EmployeeService;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

	private static Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	private EmployeeService employeeService;

	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	/**
	 * Get all Employees
	 * 
	 * @return List of all employees
	 */
	@GetMapping("/")
	public ResponseEntity<List<EmployeeDTO>> getEmployees() {
		logger.debug("Inside EmployeeController.getEmployees()");
		List<EmployeeDTO> employees = employeeService.retrieveEmployees();
		return new ResponseEntity<>(employees, HttpStatus.OK);
	}

	/**
	 * Get an employee
	 * 
	 * @param employeeId ID of Employee to be fetched
	 * @return Employee information with ID employeeId
	 */
	@GetMapping("/{employeeId}")
	public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable(name = "employeeId") Long employeeId) {
		logger.debug("Inside EmployeeController.getEmployees(),employeeId:{} ", employeeId);
		return new ResponseEntity<>(employeeService.getEmployee(employeeId), HttpStatus.OK);
	}

	/**
	 * Save a new employee
	 * 
	 * @param employeeDTO Employee information to be saved
	 * @return Saved employee
	 */
	@PostMapping("/")
	public ResponseEntity<EmployeeDTO> saveEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
		logger.debug("Inside EmployeeController.saveEmployee(),Employee:{} ", employeeDTO.toString());
		EmployeeDTO savedEmployee = employeeService.saveEmployee(employeeDTO);
		return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
	}

	/**
	 * Delete an existing employee
	 * 
	 * @param employeeId ID of employee to be deleted
	 * @return HTTPStatus.NO_CONTENT
	 */
	@DeleteMapping("/{employeeId}")
	public ResponseEntity<EmployeeDTO> deleteEmployee(@PathVariable(name = "employeeId") Long employeeId) {
		logger.debug("Inside EmployeeController.deleteEmployee(),employeeId:{} ", employeeId);
		employeeService.deleteEmployee(employeeId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	/**
	 * Update an existing employee
	 * 
	 * @param employeeDTO Employee information to be updated
	 * @param employeeId  ID of employee to be updated
	 * @return Updated employee information
	 */
	@PutMapping("/{employeeId}")
	public ResponseEntity<EmployeeDTO> updateEmployee(@Valid @RequestBody EmployeeDTO employeeDTO,
			@PathVariable(name = "employeeId") Long employeeId) {
		logger.debug("Inside EmployeeController.updateEmployee(),employeeId:{},Employee;{} ", employeeId, employeeDTO);
		EmployeeDTO updatedEmployee = employeeService.updateEmployee(employeeId, employeeDTO);
		return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
	}

}
