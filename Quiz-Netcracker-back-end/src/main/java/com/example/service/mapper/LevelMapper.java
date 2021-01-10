package com.example.service.mapper;

import com.example.dto.LevelDto;
import com.example.model.Level;
import com.example.model.Question;
import com.example.service.interfaces.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class LevelMapper implements Mapper<Level, LevelDto> {
    private final QuestionService questionService;

    @Autowired
    public LevelMapper(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Override
    public LevelDto toDto(Level entity) {
        LevelDto levelDto = new LevelDto();
        levelDto.setId(entity.getId());
        levelDto.setDescription(entity.getDescription());
        levelDto.setTitle(entity.getTitle());
        levelDto.setQuestions(entity.getQuestions()
                .stream()
                .map(Question::getId)
                .collect(Collectors.toSet()));
        return levelDto;
    }

    @Override
    public Level toEntity(LevelDto dto) {
        Set<Question> questions = dto.getQuestions()
                .stream()
                .map(questionService::getQuestionById)
                .collect(Collectors.toSet());
        Level level = new Level();
        level.setId(dto.getId());
        level.setDescription(dto.getTitle());
        level.setTitle(dto.getTitle());
        level.setQuestions(questions);
        return level;
    }

    @Override
    public LevelDto toShortDto(Level entity) {
        LevelDto levelDto = new LevelDto();
        levelDto.setDescription(entity.getDescription());
        levelDto.setTitle(entity.getTitle());
        return levelDto;
    }
}
