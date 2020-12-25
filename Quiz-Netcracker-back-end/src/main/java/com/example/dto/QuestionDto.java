package com.example.dto;

import com.example.model.Category;
import com.example.model.Game;
import com.example.model.Level;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class QuestionDto {
    UUID id;
    String title;
    String description;
    Category category;
    Level level;
    Game game;
}
