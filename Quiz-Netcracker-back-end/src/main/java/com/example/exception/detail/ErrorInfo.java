package com.example.exception.detail;

import lombok.Getter;

@Getter
public enum ErrorInfo {

    VALIDATION_ERROR("Validation error", "0001");

    private final String errorMessage;
    private final String errorCode;

    ErrorInfo(String errorMessage, String errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }
}
