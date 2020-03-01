package jp.co.axa.apidemo.exceptions;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class ErrorBody {
    String details;
    String message;
}
