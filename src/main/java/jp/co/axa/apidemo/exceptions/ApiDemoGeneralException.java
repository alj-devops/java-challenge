package jp.co.axa.apidemo.exceptions;

import org.springframework.http.HttpStatus;

/**
 * A general exception that can be used when it's an internal error
 */
public class ApiDemoGeneralException extends RuntimeException {
    protected HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    protected static String message = "AN UNKNOWN ERROR OCCURRED";

    public ApiDemoGeneralException(){
        super(message);
    }

    public ApiDemoGeneralException(HttpStatus status) {
        super(message);
        this.status = status;
    }

    public ApiDemoGeneralException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

}
