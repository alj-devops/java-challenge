package jp.co.axa.apidemo.converter;

import jp.co.axa.apidemo.dto.EmployeeDto;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Converter class to inter-convert between Domain and Data Transfer Object (DTO) for better security and efficiency
 */
@Service
public class EmployeeConverter {
    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * Domain to DTO conversion
     * @param employee Full employee information including id
     * @return DTO of employee's public information
     */
    public EmployeeDto convert(Employee employee) {
        return EmployeeDto.Builder.employeeDto()
                .withName(employee.getName())
                .withSalary(employee.getSalary())
                .withDepartment(employee.getDepartment())
                .build();
    }

    /**
     * DTO to Domain conversion
     * @param employeeDto DTO of employee's public information
     * @return Full employee information including id if it exists otherwise null
     */
    public Employee convert(EmployeeDto employeeDto) {
        Long id = employeeRepository.findId(employeeDto.getName(), employeeDto.getDepartment());
        return Employee.Builder.employee()
                .withId(id)
                .withName(employeeDto.getName())
                .withSalary(employeeDto.getSalary())
                .withDepartment(employeeDto.getDepartment())
                .build();
    }

    /**
     * DTO to Domain conversion with given id- used for Update operation
     * @param empId employee id
     * @param employeeDto DTO of employee's public information
     * @return Full employee information including id
     */
    public Employee combine(Long empId, EmployeeDto employeeDto) {
        return Employee.Builder.employee()
                .withId(empId)
                .withName(employeeDto.getName())
                .withSalary(employeeDto.getSalary())
                .withDepartment(employeeDto.getDepartment())
                .build();
    }
}
