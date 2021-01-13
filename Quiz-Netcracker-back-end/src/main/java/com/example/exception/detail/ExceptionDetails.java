package com.example.exception.detail;

import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class ExceptionDetails {
    private final Date timestamp;
    private final String errorCode;
    private final String errorMessage;
    private final Map<String, String> details;

    public ExceptionDetails(Date timestamp, String errorMessage, Map<String, String> details, String errorCode) {
        this.timestamp = timestamp;
        this.errorMessage = errorMessage;
        this.details = details;
        this.errorCode = errorCode;
    }
}
