package com.example.service.validation.validator.impl;

import com.example.service.validation.validator.CustomValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class BaseCustomValidator implements CustomValidator {
    private final Validator validator;

    @Autowired
    public BaseCustomValidator(Validator validator) {
        this.validator = validator;
    }

    @Override
    public <T> Map<String, String> validate(T entity, Class<?> constraintGroup) {
        Map<String, String> propertyViolation = new HashMap<>();
        Set<ConstraintViolation<T>> entityViolations = validator.validate(entity, constraintGroup);
        for (ConstraintViolation<T> violation: entityViolations) {
            propertyViolation.put(violation.getPropertyPath().toString(), violation.getMessage());
        }
        return propertyViolation;
    }
}
