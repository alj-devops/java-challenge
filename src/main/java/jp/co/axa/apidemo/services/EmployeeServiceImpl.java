package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.dto.response.EmployeesResponseDto;
import jp.co.axa.apidemo.dto.request.EmployeeDto;
import jp.co.axa.apidemo.dto.request.PatchUpdateEmployeeDto;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.exceptions.EmployeeNotFoundException;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeRepository employeeRepository;

    private EmployeeNotFoundException getNotFoundException(Long employeeId){
        return new EmployeeNotFoundException(String.format("Employee was not found. id: %d", employeeId));
    }


    public EmployeesResponseDto retrieveEmployees(int page, int size) {
        Page<Employee> employeePage = employeeRepository.findAll(PageRequest.of(page, size));
        Pageable pageable = employeePage.getPageable();
        return EmployeesResponseDto
                .builder()
                .employees(employeePage.getContent())
                .currentPage(pageable.getPageNumber())
                .nextPageNumber(pageable.next().getPageNumber())
                .totalPages(employeePage.getTotalPages())
                .pageSize(pageable.getPageSize())
                .build();
    }


    public Employee getEmployee(Long employeeId) throws EmployeeNotFoundException {
        return employeeRepository
                .findById(employeeId)
                .orElseThrow(() -> getNotFoundException(employeeId));
    }

    public Employee saveEmployee(EmployeeDto createEmployeeDto){
        Employee employee = Employee
                .builder()
                .department(createEmployeeDto.getDepartment())
                .name(createEmployeeDto.getName())
                .salary(createEmployeeDto.getSalary())
                .build();
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long employeeId) throws EmployeeNotFoundException{
        try {
            employeeRepository.deleteById(employeeId);
        } catch (EmptyResultDataAccessException e){
            throw getNotFoundException(employeeId);
        }
    }

    public void updateEmployee(Long employeeId, EmployeeDto updateEmployee) throws EmployeeNotFoundException {
        Employee employee = getEmployee(employeeId);
        employee.updateAllFields(updateEmployee);
        employeeRepository.save(employee);
    }
    public void patchUpdateEmployee(Long employeeId, PatchUpdateEmployeeDto patchUpdateEmployeeDto) throws EmployeeNotFoundException {
        Employee employee = getEmployee(employeeId);
        employee.patchUpdateFields(patchUpdateEmployeeDto);
        employeeRepository.save(employee);
    }
}