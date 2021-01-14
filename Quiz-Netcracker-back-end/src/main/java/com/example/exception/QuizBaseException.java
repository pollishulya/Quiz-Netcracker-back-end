package com.example.exception;

import com.example.exception.detail.ErrorInfo;
import lombok.Getter;

import java.util.Map;

@Getter
public abstract class QuizBaseException extends RuntimeException {
    private final ErrorInfo errorInfo;
    private final Map<String, String> propertyViolation;

    public QuizBaseException(ErrorInfo errorInfo, Map<String, String> propertyViolation) {
        this.errorInfo = errorInfo;
        this.propertyViolation = propertyViolation;
    }
}
