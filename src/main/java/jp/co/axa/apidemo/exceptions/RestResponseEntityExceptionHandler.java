package jp.co.axa.apidemo.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {
    
    @ExceptionHandler(value = {BusinessException.class})
    protected ResponseEntity<Object> handleConflict(
            BusinessException ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        return handleExceptionInternal(ex,
                                       ErrorBody.builder()
                                                .message(ex.getMessage())
                                                .build(),
                                       new HttpHeaders(),
                                       ex.getStatus(),
                                       request);
    }
}
