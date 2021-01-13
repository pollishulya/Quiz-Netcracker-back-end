package com.example.service.validation.annotation.impl;

import com.example.dto.AnswerDto;
import com.example.dto.QuestionDto;
import com.example.service.interfaces.QuestionService;
import com.example.service.validation.annotation.AnswersSetConstraint;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AnswersSetValidator implements ConstraintValidator<AnswersSetConstraint, QuestionDto> {
    @Override
    public void initialize(AnswersSetConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(QuestionDto questionDto, ConstraintValidatorContext constraintValidatorContext) {
        Set<AnswerDto> answerDtoSet = questionDto.getAnswersSet();
        List<AnswerDto> answerDtoList = new ArrayList<>(answerDtoSet);

        for (int i = 0; i < answerDtoList.size() - 1; i++) {
            for (int j = i + 1; j < answerDtoList.size(); j++) {
                try {
                    if (answerDtoList.get(i).getTitle().equals(answerDtoList.get(j).getTitle())) {
                        return false;
                    }
                }
                catch (NullPointerException exception) {
                    return false;
                }
            }
        }
        return true;
    }
}
