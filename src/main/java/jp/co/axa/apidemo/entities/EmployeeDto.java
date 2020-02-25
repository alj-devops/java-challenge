package jp.co.axa.apidemo.entities;

import lombok.Getter;
import lombok.Setter;

public class EmployeeDto {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private Integer salary;

    @Getter
    @Setter
    private String department;

}
