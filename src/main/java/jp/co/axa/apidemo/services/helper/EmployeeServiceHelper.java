package jp.co.axa.apidemo.services.helper;

import org.springframework.stereotype.Service;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.model.EmployeeDTO;

/**
 * Servive helper class
 *
 */
@Service
public class EmployeeServiceHelper {

	/**
	 * Method to convert Employee Entity to Employee Domain
	 * 
	 * @param employee Employee Entity
	 * @return Employee Domain
	 */
	public EmployeeDTO convertEmployeeEntityToEmployeeDomain(Employee employee) {
		EmployeeDTO employeeDTO = new EmployeeDTO();
		employeeDTO.setId(employee.getId());
		employeeDTO.setName(employee.getName());
		employeeDTO.setSalary(employee.getSalary());
		employeeDTO.setDepartment(employee.getDepartment());
		return employeeDTO;
	}

	/**
	 * Method to convert Employee Domain to Employee Entity
	 * 
	 * @param employeeDTO Employee Domain
	 * @return Employee Entity
	 */
	public Employee convertEmployeeDomainToEmployeeEntity(EmployeeDTO employeeDTO) {
		Employee employee = new Employee();
		employee.setId(employeeDTO.getId());
		employee.setName(employeeDTO.getName());
		employee.setSalary(employeeDTO.getSalary());
		employee.setDepartment(employeeDTO.getDepartment());
		return employee;
	}

	/**
	 * Method to convert Employee Domain to Employee Entity for Update operation
	 * 
	 * @param employeeDTO Employee Domain
	 * @return Employee Entity
	 */
	public Employee convertEmployeeDomainToEmployeeEntityForUpdate(EmployeeDTO employeeDTO, Employee employee) {
		employee.setName(employeeDTO.getName());
		employee.setSalary(employeeDTO.getSalary());
		employee.setDepartment(employeeDTO.getDepartment());
		return employee;
	}
}
