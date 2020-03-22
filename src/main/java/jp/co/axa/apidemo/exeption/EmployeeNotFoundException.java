package jp.co.axa.apidemo.exeption;

/**
 * API demo Business exception class Used to throw an exception when Employee
 * does not exists
 *
 */
public class EmployeeNotFoundException extends RuntimeException {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 6123694005864171894L;

	public EmployeeNotFoundException(Long id) {
		super("Employee with ID: " + id + " not found");
	}
}
