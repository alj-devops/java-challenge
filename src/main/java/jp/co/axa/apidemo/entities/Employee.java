package jp.co.axa.apidemo.entities;

import jp.co.axa.apidemo.dto.request.EmployeeDto;
import jp.co.axa.apidemo.dto.request.PatchUpdateEmployeeDto;
import jp.co.axa.apidemo.interfaces.VoidFunction;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="EMPLOYEE")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="EMPLOYEE_NAME")
    private String name;

    @Column(name="EMPLOYEE_SALARY")
    private Integer salary;

    @Column(name="DEPARTMENT")
    private String department;

    /**
     * a helper function to help reduce redundant if statements
     * @param field This object's field
     * @param success if true, we can go ahead and call the function
     * @param <T> return the field's value
     */
    public <T> void updateFields(T field, VoidFunction<Void> success){
        if (field != null){
            success.call();
        }
    }

    /**
     * will override all fields.
     * @param employeeDto
     */
    public void updateAllFields(EmployeeDto employeeDto){
        this.name = employeeDto.getName();
        this.salary = employeeDto.getSalary();
        this.department = employeeDto.getDepartment();
    }

    /**
     * will only write to fields available
     * @param employeeDto PatchUpdateEmployeeDto
     */
    public void patchUpdateFields(PatchUpdateEmployeeDto employeeDto){
        updateFields(employeeDto.getName(), ()-> this.name = employeeDto.getName());
        updateFields(employeeDto.getSalary(), ()-> this.salary = employeeDto.getSalary());
        updateFields(employeeDto.getDepartment(), ()-> this.department = employeeDto.getDepartment());
    }
}
