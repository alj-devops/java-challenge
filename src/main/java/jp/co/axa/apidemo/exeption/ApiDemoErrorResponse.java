package jp.co.axa.apidemo.exeption;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * Customized Error response
 *
 */
@Data
public class ApiDemoErrorResponse {

	/**
	 * Error timestamp
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
	private LocalDateTime timestamp;

	/**
	 * HTTPStatus code
	 */
	private int status;

	/**
	 * Exception message
	 */
	private String error;
}
