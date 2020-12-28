package com.example.service.validation;

import com.example.service.validation.impl.AnswerTitleValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = AnswerTitleValidator.class)
//TODO Повесить над классом Question и Answer
//@Target({ ElementType })
@Retention(RetentionPolicy.RUNTIME)
public @interface AnswerTitleConstraint {
    String message() default "Wrong answer title";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
