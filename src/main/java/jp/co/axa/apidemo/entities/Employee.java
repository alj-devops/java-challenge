package jp.co.axa.apidemo.entities;

import lombok.*;

import javax.persistence.*;

/**
 * Entity class that maps the EMPLOYEE table
 *
 * @author Thea Krista
 * @version 1.0
 * @since api-demo-0.0.1
 */
@Data
@Entity
@Table(name="EMPLOYEE")
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    /**
     * Employee constructor
     *
     * @param name - employee name
     * @param salary - salary name
     * @param department - department name
     */
    public Employee(String name, Integer salary, String department) {
        this.name = name;
        this.salary = salary;
        this.department = department;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="ID", nullable = false, updatable = false)
    private Long id;

    @Column(name="EMPLOYEE_NAME")
    private String name;

    @Column(name="EMPLOYEE_SALARY")
    private Integer salary;

    @Column(name="DEPARTMENT")
    private String department;

}
