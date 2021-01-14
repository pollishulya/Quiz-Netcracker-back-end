package com.example.service.validation.annotation.impl;

import com.example.service.validation.annotation.UUIDPattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UUIDPatternValidator implements ConstraintValidator<UUIDPattern, UUID> {
    @Override
    public void initialize(UUIDPattern constraintAnnotation) {
    }

    @Override
    public boolean isValid(UUID uuid, ConstraintValidatorContext constraintValidatorContext) {
        if (uuid == null) {
            return false;
        }
        Pattern p = Pattern.compile("^[0-9a-f]{8}-([0-9a-f]{4}-){3}[0-9a-f]{12}$");
        Matcher m = p.matcher(uuid.toString());
        return m.matches();
    }
}
