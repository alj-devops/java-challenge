package jp.co.axa.apidemo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class Employee {

	/**
	 * Employee ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * Employee name
	 */
	@Column(name = "EMPLOYEE_NAME")
	private String name;

	/**
	 * Employee salary
	 */
	@Column(name = "EMPLOYEE_SALARY")
	private Integer salary;

	/**
	 * Employee's department name
	 */
	@Column(name = "DEPARTMENT")
	private String department;

}
