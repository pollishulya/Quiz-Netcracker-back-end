package com.example.dto;

import com.example.model.Answer;
import com.example.model.Category;
import com.example.model.Game;
import com.example.model.Level;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Value;

import java.util.Set;
import java.util.UUID;

@Value
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestionDto {
    UUID id;
    String title;
    String description;
    Category category;
    Level level;
    Game game;
    Set<AnswerDto> answersSet;
}
