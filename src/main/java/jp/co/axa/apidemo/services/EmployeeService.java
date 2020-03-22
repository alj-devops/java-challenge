package jp.co.axa.apidemo.services;

import java.util.List;

import jp.co.axa.apidemo.model.EmployeeDTO;

public interface EmployeeService {

	List<EmployeeDTO> retrieveEmployees();

	EmployeeDTO getEmployee(Long employeeId);

	EmployeeDTO saveEmployee(EmployeeDTO employee);

	void deleteEmployee(Long employeeId);

	EmployeeDTO updateEmployee(Long employeeId, EmployeeDTO employee);
}