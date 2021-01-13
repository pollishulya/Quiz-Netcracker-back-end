package com.example.service.validation.validator.impl;

import com.example.service.validation.validator.CustomValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Service
public class BaseCustomValidator implements CustomValidator {
    private final Validator validator;

    @Autowired
    public BaseCustomValidator(Validator validator) {
        this.validator = validator;
    }

    @Override
    public <T> String validate(T entity, Class<?> constraintGroup) {
        StringBuilder message = new StringBuilder();
        Set<ConstraintViolation<T>> entityViolations = validator.validate(entity, constraintGroup);
        for (ConstraintViolation<T> violation: entityViolations) {
            message.append(violation.getPropertyPath().toString()).append(": ").append(violation.getMessage()).append("; ");
        }
        return message.toString();
    }
}
