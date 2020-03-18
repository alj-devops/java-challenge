package jp.co.axa.apidemo.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
/**
 * Holds all related api error codes. Please make sure to not repeat error codes!
 */
public enum ApiErrorCodes {
    INVALID_REQUEST(getErrorCode("0001"), "INVALID_REQUEST"),
    EMPLOYEE_NOT_FOUND(getErrorCode("1001"), "EMPLOYEE_NOT_FOUND"),
    INTERNAL_SERVER_ERROR(getErrorCode("0000"), "INTERNAL_SERVER_ERROR");

    private final String errorCode;
    private final String errorMessage;

    private static final String serviceErrorCode = "911";
    public static String getErrorCode(String value) {
        return serviceErrorCode + value;
    }
}
