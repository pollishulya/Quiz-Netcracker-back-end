package com.example.dto;

import com.example.model.Answer;
import com.example.model.Category;
import com.example.model.Game;
import com.example.model.Level;
import lombok.Builder;
import lombok.Value;

import java.util.Set;
import java.util.UUID;

@Value
@Builder
public class QuestionDto {
    private UUID id;
    private String title;
    private String description;
    private Category category;
    private Level level;
    private Set<Answer> answers;
    private Set<Game> games;

}
