package jp.co.axa.apidemo;

import com.fasterxml.jackson.databind.ObjectMapper;
import jp.co.axa.apidemo.dto.response.EmployeesResponseDto;
import jp.co.axa.apidemo.dto.request.EmployeeDto;
import jp.co.axa.apidemo.entities.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestData {

    protected ObjectMapper objectMapper = new ObjectMapper();

    protected String createRequestString = "{\n" +
            "\t\"department\": \"HR\",\n" +
            "\t\"name\": \"bob\",\n" +
            "\t\"salary\": 111\n" +
            "}";
    protected String updateRequestString = "{\n" +
            "\t\"department\": \"HR\",\n" +
            "\t\"name\": \"Bob Smith\",\n" +
            "\t\"salary\": 111\n" +
            "}";

    protected String patchUpdateRequestString = "{\n" +
            "\t\"department\": \"HR\"\n" +
            "}";

    protected List<Employee> getListOfEmployees(int howMany){
        return IntStream.range(0, howMany)
                .mapToObj(this::createEmployee)
                .collect(Collectors.toList());
    }

    protected <T> Page<T> getPage(List<T> items, int page, int size, int total){
        return new PageImpl<>(items, PageRequest.of(page, size), total);
    }

    protected Employee createEmployee(int id){
        return Employee.builder()
                .department("department " + id)
                .name("name " + id)
                .salary(id + 50000)
                .id((long) (id + 1))
                .build();
    }

    protected Employee getSingleEmployee(){
        return createEmployee(0);
    }

    protected Employee getBobEmployee() throws Exception{
        Employee employee = objectMapper.readValue(createRequestString, Employee.class);
        employee.setId(1L);
        return employee;
    }

    protected Employee  getBobUpdated() throws Exception{
        Employee employee = getBobEmployee();
        employee.setName("Bob Smith");
        return employee;
    }

    protected EmployeesResponseDto getEmployeesDto(){
        return EmployeesResponseDto.builder()
                .employees(getListOfEmployees(10))
                .nextPageNumber(1)
                .pageSize(10)
                .totalPages(5)
                .currentPage(0)
                .build();
    }

    protected EmployeeDto getEmployeeDto(){
        return EmployeeDto.builder()
                .name("Bob smith")
                .department("HR")
                .salary(111)
                .build();

    }


}
