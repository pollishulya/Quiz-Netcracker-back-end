package com.example.exception.detail;

import lombok.Data;

import java.util.Date;

@Data
public class ExceptionDetails {
    private final Date timestamp;
    private final String message;
    private final String details;

    public ExceptionDetails(Date timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }
}
