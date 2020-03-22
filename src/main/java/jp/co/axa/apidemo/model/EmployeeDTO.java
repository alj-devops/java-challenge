package jp.co.axa.apidemo.model;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data transfer Object for Employee entity
 *
 */
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class EmployeeDTO {

	/**
	 * Employee ID
	 */
	private Long id;

	/**
	 * Employee name
	 */
	@NotNull(message = "Name should not be null")
	private String name;

	/**
	 * Employee salary
	 */
	@NotNull(message = "Salary should not be null")
	private Integer salary;

	/**
	 * Employee's department name
	 */
	@NotNull(message = "Department should not be null")
	private String department;

}
