package jp.co.axa.apidemo.errorhandler;

import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

public class RestApiErrorResponse {

	private List<Error> errors = new ArrayList<>();

	private String api;

	private ErrorCodeEnum code;

	private String message;

	public enum ErrorCodeEnum {
		VALIDATION_ERROR("INPUT_VALIDATION_ERROR"),

		INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR");

		private String value;

		ErrorCodeEnum(String value) {
			this.value = value;
		}
	}

	@ApiModelProperty(value = "list of errors.")
	public List<Error> getErrors() {
		return errors;
	}

	public void setErrors(List<Error> errors) {
		this.errors = errors;
	}

	@ApiModelProperty(value = "reference of the API being called.", example = "/employees")
	public String getApi() {
		return api;
	}

	public void setApi(String api) {
		this.api = api;
	}

	@ApiModelProperty(value = "code describing the type of error.", example = "VALIDATION_ERROR")
	public ErrorCodeEnum getCode() {
		return code;
	}

	public void setCode(ErrorCodeEnum code) {
		this.code = code;
	}

	@ApiModelProperty(value = "error message.", example = "string")
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Creates error response instance by specified error code and error field.
	 *
	 * @param errorCode error code
	 * @param errorField error field
	 * @return RestApiErrorResponse
	 */
	public static RestApiErrorResponse error(String errorCode, String errorField) {
		RestApiErrorResponse errorResponse = new RestApiErrorResponse();
		errorResponse.errors.add(new Error(errorCode, errorField));
		return errorResponse;
	}

	public void addError(String errorCode, String errorField) {
		errors.add(new Error(errorCode, errorField));
	}

	@Override
	public String toString() {
		return "RestApiErrorResponse [errors=" + errors + ", api=" + api + ", code=" + code + ", message=" + message + "]";
	}

}
