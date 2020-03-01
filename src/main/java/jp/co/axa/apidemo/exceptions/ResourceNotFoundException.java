package jp.co.axa.apidemo.exceptions;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class ResourceNotFoundException extends BusinessException {
    
    private static final String DEFAULT_MESSAGE = "Resource not found";
    private static final HttpStatus STATUS = NOT_FOUND;
    
    public ResourceNotFoundException() {
        super(DEFAULT_MESSAGE, STATUS);
    }
    
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause, STATUS);
    }
    
    public ResourceNotFoundException(String message) {
        super(message, STATUS);
    }
    
    public ResourceNotFoundException(Throwable cause) {
        super(cause, STATUS);
    }
    
}
