package jp.co.axa.apidemo.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Hibernate Domain class
 * EMPLOYEE table shall have a unique key combination on (EMPLOYEE_NAME, DEPARTMENT) pair
 */
@Entity
@Table(name = "EMPLOYEE",
        indexes = {
                @Index(name = "index_nameDept", columnList = "EMPLOYEE_NAME, DEPARTMENT", unique = true)
        })
public class Employee {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column(name = "EMPLOYEE_NAME")
    private String name;

    @Getter
    @Setter
    @Column(name = "EMPLOYEE_SALARY")
    private Integer salary;

    @Getter
    @Setter
    @Column(name = "DEPARTMENT")
    private String department;

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                ", department='" + department + '\'' +
                '}';
    }
// 'Step Builder Pattern Generator' has been used to generate the below code

    public static interface IdStep {
        NameStep withId(Long id);
    }

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
        Employee build();
    }

    public static class Builder implements IdStep, NameStep, SalaryStep, DepartmentStep, BuildStep {
        private Long id;
        private String name;
        private Integer salary;
        private String department;

        private Builder() {
        }

        public static IdStep employee() {
            return new Builder();
        }

        @Override
        public NameStep withId(Long id) {
            this.id = id;
            return this;
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
        public Employee build() {
            Employee employee = new Employee();
            employee.setId(this.id);
            employee.setName(this.name);
            employee.setSalary(this.salary);
            employee.setDepartment(this.department);
            return employee;
        }
    }
}
