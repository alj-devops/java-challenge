package jp.co.axa.apidemo.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class BusinessException extends RuntimeException {
    
    @Getter
    protected HttpStatus status;
    
    public BusinessException() {
        this(HttpStatus.BAD_REQUEST);
    }
    
    public BusinessException(final HttpStatus status) {
        super();
        this.status = status;
    }
    
    public BusinessException(final String message, final Throwable cause) {
        this(message, cause, HttpStatus.BAD_REQUEST);
    }
    
    public BusinessException(final String message, final Throwable cause, final HttpStatus status) {
        super(message, cause);
        this.status = status;
    }
    
    public BusinessException(final String message) {
        super(message);
    }
    
    public BusinessException(final String message, final HttpStatus status) {
        super(message);
        this.status = status;
    }
    
    public BusinessException(final Throwable cause) {
        this(cause, HttpStatus.BAD_REQUEST);
    }
    
    public BusinessException(final Throwable cause, final HttpStatus status) {
        super(cause);
        this.status = status;
    }
}
