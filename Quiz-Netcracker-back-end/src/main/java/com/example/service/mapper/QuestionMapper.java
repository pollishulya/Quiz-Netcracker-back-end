package com.example.service.mapper;

import com.example.dto.QuestionDto;
import com.example.model.Question;
import org.springframework.stereotype.Component;

@Component
public class QuestionMapper {
    public QuestionDto toQuestionDto(final Question question){
        return QuestionDto.builder()
                .id(question.getId())
                .title(question.getDescription())
                .description(question.getDescription())
                .category(question.getCategory())
                .level(question.getLevel())
                .build();

    }

    public Question fromQuestionDto(final QuestionDto questionDto){
        return Question.builder()
                .id(questionDto.getId())
                .title(questionDto.getTitle())
                .description(questionDto.getDescription())
                .category(questionDto.getCategory())
                .level(questionDto.getLevel())
                .game(questionDto.getGame())
                .build();
    }

    public  QuestionDto toShortQuestionDto(final Question question){
        return QuestionDto.builder()
                .id(question.getId())
                .title(question.getTitle())
                .description(question.getDescription())
                .category(question.getCategory())
                .level(question.getLevel())
                .build();
    }
}