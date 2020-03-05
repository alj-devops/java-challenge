package jp.co.axa.apidemo.server.web;

import jp.co.axa.apidemo.client.ClientEmployee;
import jp.co.axa.apidemo.entities.Employee;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class ResourceMapper {
    
    public static Employee fromClientEmployee(final ClientEmployee input) {
        return Employee.builder()
                       .department(input.getDepartment())
                       .id(input.getId())
                       .name(input.getName())
                       .salary(input.getSalary())
                       .build();
    }
    
    public static ClientEmployee toClientEmployee(final Employee input) {
        return ClientEmployee.builder()
                             .department(input.getDepartment())
                             .id(input.getId())
                             .name(input.getName())
                             .salary(input.getSalary())
                             .build();
    }
    
    public static List<ClientEmployee> toClientEmployeeList(final List<Employee> inputList) {
        return inputList.stream().map(ResourceMapper::toClientEmployee).collect(toList());
    }
}
