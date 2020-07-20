package jp.co.axa.apidemo.errorhandler;

import jp.co.axa.apidemo.controllers.EmployeeController;
import jp.co.axa.apidemo.errorhandler.RestApiErrorResponse.ErrorCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice(assignableTypes = {EmployeeController.class})
public class HttpExceptionHandler
		extends ResponseEntityExceptionHandler {

	private static final Logger LOG = LoggerFactory.getLogger(HttpExceptionHandler.class);

	// error handle for @Valid
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		LOG.info("handleMethodArgumentNotValid occurred");

		RestApiErrorResponse errorResponse = new RestApiErrorResponse();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			errorResponse.addError(error.getCode(), error.getField());
		}
		errorResponse.setCode(ErrorCodeEnum.VALIDATION_ERROR);
		errorResponse.setMessage(ErrorCodeEnum.valueOf(ErrorCodeEnum.VALIDATION_ERROR.toString()).toString());
		errorResponse.setApi(((ServletWebRequest) request).getRequest().getRequestURI());
		LOG.info("MethodArgumentNotValidException::{}" , errorResponse);
		return new ResponseEntity<>(errorResponse, headers, status);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		RestApiErrorResponse errorResponse = new RestApiErrorResponse();
		errorResponse.setCode(ErrorCodeEnum.VALIDATION_ERROR);
		errorResponse.setMessage(ex.getMessage());
		errorResponse.setApi(((ServletWebRequest) request).getRequest().getRequestURI());
		LOG.info("HttpMessageNotReadableException::{}" , errorResponse);
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<RestApiErrorResponse> handleDefaultException(Exception ex, WebRequest request) {
		LOG.info("handleMyPageException occurred");
		RestApiErrorResponse errorResponse = new RestApiErrorResponse();
		errorResponse.setCode(ErrorCodeEnum.INTERNAL_SERVER_ERROR);
		errorResponse.setMessage(ErrorCodeEnum.valueOf(ErrorCodeEnum.INTERNAL_SERVER_ERROR.toString()).toString());
		errorResponse.setApi(((ServletWebRequest) request).getRequest().getRequestURI());
		LOG.info("Exception::", ex);
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
