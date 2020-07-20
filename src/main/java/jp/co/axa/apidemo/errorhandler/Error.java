package jp.co.axa.apidemo.errorhandler;

public class Error {

	private String errorcode;
	private String errorname;

	public Error(String errorCode, String errorField) {
		this.errorname = errorField;
		this.setCode(errorCode);
	}

	public String getCode() {
		return errorcode;
	}

	/**
	 * set error code and fill the message from message source bundle.
	 *
	 * @param code
	 *            error code.
	 */
	public void setCode(String code) {
		this.errorcode = code;

	}

	public String getField() {
		return errorname;
	}

	public void setField(String field) {
		this.errorname = field;
	}
}
