package com.example.service.validation.validator;

import java.util.Map;

public interface CustomValidator {
    <T> Map<String, String> validate(T entity, Class<?> constraintGroup);
}
