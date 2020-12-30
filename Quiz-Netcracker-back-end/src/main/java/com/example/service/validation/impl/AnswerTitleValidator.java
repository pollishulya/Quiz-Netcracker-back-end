package com.example.service.validation.impl;

import com.example.model.Answer;
import com.example.model.Question;
import com.example.service.validation.AnswerTitleConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AnswerTitleValidator implements ConstraintValidator<AnswerTitleConstraint, Answer> {
    @Override
    public void initialize(AnswerTitleConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(Answer answer, ConstraintValidatorContext constraintValidatorContext) {
        String title = answer.getTitle();
        if (title == null) {
            return false;
        }
        Question answerQuestion = answer.getQuestion();
        for (Answer anotherAnswer: answerQuestion.getAnswersSet()) {
            if (anotherAnswer.getTitle().equals(title)) {
                return false;
            }
        }
        return true;
    }
}
