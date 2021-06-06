package org.twitter.security.dto;



import java.util.Date;

import org.springframework.http.HttpStatus;
import org.twitter.security.enums.ErrorCode;

/**
 * 
 * @author someshKumar
 *
 */
public class ErrorResponseDTO {
    // HTTP Response Status Code
    private final HttpStatus status;

    // General Error message
    private final String message;

    // Error code
    private final ErrorCode errorCode;

    private final Date timestamp;

    protected ErrorResponseDTO(final String message, final ErrorCode errorCode, HttpStatus status) {
        this.message = message;
        this.errorCode = errorCode;
        this.status = status;
        this.timestamp = new Date();
    }

    public static ErrorResponseDTO of(final String message, final ErrorCode errorCode, HttpStatus status) {
        return new ErrorResponseDTO(message, errorCode, status);
    }

    public Integer getStatus() {
        return status.value();
    }

    public String getMessage() {
        return message;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
