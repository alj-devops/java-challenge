package jp.co.axa.apidemo.controllerAdvice;

import jp.co.axa.apidemo.constants.ApiErrorCodes;
import jp.co.axa.apidemo.dto.response.ErrorResponseDto;
import jp.co.axa.apidemo.exceptions.EmployeeNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private static final String DEFAULT_EXCEPTION_FORMAT = "Exception type: {}. Cause: {}";

    private ErrorResponseDto handleErrorResponse(ApiErrorCodes apiErrorCodes, String description){
        return ErrorResponseDto.builder()
                .timestamp(new Date())
                .errorCode(apiErrorCodes.getErrorCode())
                .message(apiErrorCodes.getErrorMessage())
                .description(description)
                .build();
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object>  generalException(Exception e) throws IOException {
        return new ResponseEntity<>(handleErrorResponse(ApiErrorCodes.INTERNAL_SERVER_ERROR, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<Object> employeeNotFoundExceptionHandler(EmployeeNotFoundException e){
        log.error(DEFAULT_EXCEPTION_FORMAT, e.getClass(), e.getMessage(), e);
        return new ResponseEntity<>(handleErrorResponse(ApiErrorCodes.EMPLOYEE_NOT_FOUND, e.getMessage()), HttpStatus.NOT_FOUND);
    }

    /**
     * This error occurs when a validation fails in the dto's validation.
     * @param e MethodArgumentNotValidException: the error to use to get necessary information.
     * @return ResponseEntity with out custom response object
     * @throws IOException
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object>  methodArgumentNotValidException(MethodArgumentNotValidException e) throws IOException {
        BindingResult bindingResult = e.getBindingResult();
        String errorMessage = null;
        for (FieldError fieldError: bindingResult.getFieldErrors()) {
            errorMessage = handleFieldErrors(fieldError, errorMessage);
        }
        return new ResponseEntity<>(handleErrorResponse(ApiErrorCodes.INVALID_REQUEST, errorMessage), HttpStatus.BAD_REQUEST);
    }
    private String handleFieldErrors(FieldError fieldError, String start) {
        StringBuilder builder = Optional.ofNullable(start)
                .map(s -> new StringBuilder(s + ", "))
                .orElse(new StringBuilder());
        builder.append("field: ");
        builder.append(fieldError.getField());
        builder.append(". ");
        builder.append("error: ");
        builder.append(fieldError.getDefaultMessage());
        return builder.toString();
    }

    /**
     * This error occurs with a @Validation exception not body related(like pathVariable)
     * @param e ConstraintViolationException. Just grabs the message right from it
     * @return ResponseEntity with out custom response object
     * @throws IOException
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object>  constraintViolationException(ConstraintViolationException e) throws IOException {
        return new ResponseEntity<>(handleErrorResponse(ApiErrorCodes.INVALID_REQUEST, e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
