package com.example.service.mapper;

import com.example.dto.AnswerDto;
import com.example.model.Answer;
import org.springframework.stereotype.Component;

@Component
public class AnswerMapper {

    public AnswerDto toAnswerDto(final Answer answer){
        return AnswerDto.builder()
                .id(answer.getId())
                .title(answer.getTitle())
                .right(answer.isRight())
                .build();
    }

    public Answer fromAnswerDto(final AnswerDto answerDto){
        return Answer.builder()
                .id(answerDto.getId())
                .title(answerDto.getTitle())
                .right(answerDto.isRight())
                .build();
    }

    public AnswerDto toShortAnswerDto(final Answer answer){
        return AnswerDto.builder()
                .title(answer.getTitle())
                .build();
    }
}
