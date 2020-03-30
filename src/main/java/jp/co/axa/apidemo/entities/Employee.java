package jp.co.axa.apidemo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;


@Entity
@Table(name="EMPLOYEE")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name="EMPLOYEE_NAME")
    @NotEmpty(message = "Please provide a name")
    private String name;

    @Column(name="EMPLOYEE_SALARY")
    @Digits(integer=10, fraction=0, message = "Please provide Digits")
    private Integer salary;

    @Column(name="DEPARTMENT")
    private String department;

}
