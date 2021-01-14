package com.example.service.validation.annotation;

import com.example.service.validation.annotation.impl.UUIDPatternValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = UUIDPatternValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UUIDPattern {
    String message() default "UUID doesn't match format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
