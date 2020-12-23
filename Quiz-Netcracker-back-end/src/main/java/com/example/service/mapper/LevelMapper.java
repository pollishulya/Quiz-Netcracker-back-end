package com.example.service.mapper;

import com.example.dto.LevelDto;
import com.example.model.Level;
import org.springframework.stereotype.Component;

@Component
public class LevelMapper {
    public LevelDto toLevelDto(final Level level){
        return LevelDto.builder()
                .id(level.getId())
                .description(level.getDescription())
                .title(level.getTitle())
                .questions(level.getQuestions())
                .build();
    }


    public Level fromLevelDto(final LevelDto levelDto){
        return Level.builder()
                .id(levelDto.getId())
                .description(levelDto.getDescription())
                .title(levelDto.getTitle())
                .questions(levelDto.getQuestions())
                .build();
    }

    public LevelDto toShortLevelDto(final Level level){
        return LevelDto.builder()
                .description(level.getDescription())
                .title(level.getTitle())
                .build();
    }

}
