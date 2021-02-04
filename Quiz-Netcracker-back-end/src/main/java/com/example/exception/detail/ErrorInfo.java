package com.example.exception.detail;

import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public enum ErrorInfo {

    ARGUMENT_NOT_VALID("title.ArgumentNotValid", "0001", HttpStatus.BAD_REQUEST),
    RESOURCE_NOT_FOUND("title.ResourceNotFound", "0002", HttpStatus.NOT_FOUND),
    INTERNAL_SERVER_ERROR("title.InternalServerError", "0003", HttpStatus.INTERNAL_SERVER_ERROR),
    DELETE_ENTITY_ERROR("title.DeleteEntityError", "0004", HttpStatus.NOT_FOUND),
    AUTHORIZATION_ERROR("title.AuthorizationError", "0005", HttpStatus.BAD_REQUEST);

    private final String errorTitle;
    private final String errorCode;
    private final HttpStatus httpStatus;

    ErrorInfo(String errorTitle, String errorCode, HttpStatus httpStatus) {
        this.errorTitle = errorTitle;
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }
}
