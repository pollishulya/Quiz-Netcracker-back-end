package com.example.exception.handler;

import com.example.exception.ArgumentNotValidException;
import com.example.exception.QuizBaseException;
import com.example.exception.detail.ExceptionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(QuizBaseException.class)
    public ResponseEntity<?> argumentNotValidHandling(QuizBaseException exception) {
        ExceptionDetails exceptionDetails = new ExceptionDetails(new Date(), exception.getErrorInfo().getErrorMessage(),
                exception.getPropertyViolation(), exception.getErrorInfo().getErrorCode());
        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }
}
