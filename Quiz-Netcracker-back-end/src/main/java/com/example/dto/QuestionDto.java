package com.example.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class QuestionDto {

    private UUID id;
    private String title;
    private String description;
    private CategoryDto category;

    // TODO add LevelConverters an so on...
    // private LevelDto level;

    //TODO add AnswerConverters an so on...
    //private Set<Answer> answersSet;

    //TODO add GameConverters an so on...
    //private Set<Game> gamesSet;
}
