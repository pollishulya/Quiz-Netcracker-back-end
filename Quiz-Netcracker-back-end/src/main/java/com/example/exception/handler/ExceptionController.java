package com.example.exception.handler;

import com.example.exception.ArgumentNotValidException;
import com.example.exception.detail.ExceptionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(ArgumentNotValidException.class)
    public ResponseEntity<?> argumentNotValidHandling(ArgumentNotValidException exception) {
        ExceptionDetails exceptionDetails = new ExceptionDetails(new Date(), "Validation error",
                exception.getMessage());
        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }
}
