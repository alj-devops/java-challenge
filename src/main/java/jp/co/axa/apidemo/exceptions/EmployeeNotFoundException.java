package jp.co.axa.apidemo.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Use this when an employee can't be found.
 */
public class EmployeeNotFoundException extends ApiDemoGeneralException {

    public EmployeeNotFoundException() {
        super(HttpStatus.NOT_FOUND);
    }
    public EmployeeNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }

}
