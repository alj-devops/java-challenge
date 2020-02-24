package jp.co.axa.apidemo.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * Data Transfer Object class to hide employee's internal information
 */
@Getter
@Setter
public class EmployeeDto {
    // public information fields of Employee

    @NotBlank(message = "Employee name is mandatory")
    private String name;

    @Min(value = 1, message = "Salary must be greater than 0")
    private Integer salary;

    @NotBlank(message = "Department is mandatory")
    private String department;

    @Override
    public String toString() {
        return "EmployeeDto{" +
                "name='" + name + '\'' +
                ", salary=" + salary +
                ", department='" + department + '\'' +
                '}';
    }
// 'Step Builder Pattern Generator' has been used to generate the below code

    public static interface NameStep {
        SalaryStep withName(String name);
    }

    public static interface SalaryStep {
        DepartmentStep withSalary(Integer salary);
    }

    public static interface DepartmentStep {
        BuildStep withDepartment(String department);
    }

    public static interface BuildStep {
        EmployeeDto build();
    }

    public static class Builder implements NameStep, SalaryStep, DepartmentStep, BuildStep {
        private String name;
        private Integer salary;
        private String department;

        private Builder() {
        }

        public static NameStep employeeDto() {
            return new Builder();
        }

        @Override
        public SalaryStep withName(String name) {
            this.name = name;
            return this;
        }

        @Override
        public DepartmentStep withSalary(Integer salary) {
            this.salary = salary;
            return this;
        }

        @Override
        public BuildStep withDepartment(String department) {
            this.department = department;
            return this;
        }

        @Override
        public EmployeeDto build() {
            EmployeeDto employeeDto = new EmployeeDto();
            employeeDto.setName(this.name);
            employeeDto.setSalary(this.salary);
            employeeDto.setDepartment(this.department);
            return employeeDto;
        }
    }
}
