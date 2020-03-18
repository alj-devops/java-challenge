package jp.co.axa.apidemo.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@RequiredArgsConstructor
@Builder
@Getter
/**
 * Our own custom error response
 */
public class ErrorResponseDto {
    private final Date timestamp;
    private final String errorCode;
    private final String message;
    private final String description;
}
