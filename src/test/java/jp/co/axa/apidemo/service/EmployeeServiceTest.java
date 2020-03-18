package jp.co.axa.apidemo.service;

import jp.co.axa.apidemo.TestData;
import jp.co.axa.apidemo.dto.response.EmployeesResponseDto;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import jp.co.axa.apidemo.services.EmployeeService;
import jp.co.axa.apidemo.services.EmployeeServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

public class EmployeeServiceTest extends TestData {

    private EmployeeService employeeService;
    private EmployeeRepository employeeRepository;

    @Before
    public void init(){
        employeeRepository = Mockito.mock(EmployeeRepository.class);
        employeeService = new EmployeeServiceImpl(employeeRepository);
    }

    @Test
    public void success_retrieveEmployees(){
        List<Employee> employeeList = getListOfEmployees(1);
        when(employeeRepository.findAll(any(PageRequest.class))).thenReturn(getPage(employeeList, 0, 1, 1));
        EmployeesResponseDto employeesResponseDto = employeeService.retrieveEmployees(0, 1);
        assert employeesResponseDto.getCurrentPage() == 0;
        assert employeesResponseDto.getPageSize() == 1;
        assert Objects.deepEquals(employeesResponseDto.getEmployees(), employeeList) ;
    }

    @Test
    public void success_getSingleEmployee(){
        Employee employee = getSingleEmployee();
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(employee));
        Employee employeeServiceEmployee = employeeService.getEmployee(1L);
        assert Objects.deepEquals(employeeServiceEmployee, employee);
    }

    @Test
    public void success_saveEmployee() throws Exception{
        Employee employee = getBobUpdated();
        when(employeeRepository.save(any())).thenReturn(employee);
        Employee employeeServiceEmployee = employeeService.saveEmployee(getEmployeeDto());
        assert Objects.deepEquals(employeeServiceEmployee, employee);
    }
}
