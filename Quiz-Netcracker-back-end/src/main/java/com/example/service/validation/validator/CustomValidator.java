package com.example.service.validation.validator;

public interface CustomValidator {
    <T> String validate(T entity, Class<?> constraintGroup);
}
