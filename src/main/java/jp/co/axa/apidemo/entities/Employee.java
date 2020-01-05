package jp.co.axa.apidemo.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Data structure to handle the Employees' information.
 *
 * @author AXA
 *
 */
@Entity
@Table(name = "EMPLOYEE")
public class Employee {

    /**
     * employee Id will increased by itself.
     */
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Employee Name.
     */
    @Getter
    @Setter
    @NotBlank(message = "Name is mandatory")
    @Column(name = "EMPLOYEE_NAME")
    private String name;

    /**
     * Employee Salary.
     */
    @Getter
    @Setter
    @Min(value = 0, message = "Salary must be overe 0")
    @NotNull
    @Column(name = "EMPLOYEE_SALARY")
    private Integer salary;

    /**
     * Employee Department.
     */
    @Getter
    @Setter
    @NotBlank(message = "Department is mandatory")
    @Column(name = "DEPARTMENT")
    private String department;

}
