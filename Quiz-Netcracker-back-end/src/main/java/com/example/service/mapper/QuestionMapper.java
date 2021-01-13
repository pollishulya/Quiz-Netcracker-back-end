package com.example.service.mapper;

import com.example.dto.QuestionDto;
import com.example.model.*;
import com.example.service.interfaces.AnswerService;
import com.example.service.interfaces.CategoryService;
import com.example.service.interfaces.GameService;
import com.example.service.interfaces.LevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class QuestionMapper implements Mapper<Question, QuestionDto> {
    private final GameService gameService;
    private final CategoryService categoryService;
    private final LevelService levelService;
    private final AnswerService answerService;

    @Autowired
    public QuestionMapper(GameService gameService, CategoryService categoryService, LevelService levelService, AnswerService answerService) {
        this.gameService = gameService;
        this.categoryService = categoryService;
        this.levelService = levelService;
        this.answerService = answerService;
    }

    @Override
    public QuestionDto toDto(Question entity) {
        QuestionDto questionDto = new QuestionDto();
        questionDto.setId(entity.getId());
        questionDto.setTitle(entity.getTitle());
        questionDto.setDescription(entity.getDescription());
//        questionDto.setCategory(entity.getCategory().getId());
//        questionDto.setLevel(entity.getLevel().getId());
//        questionDto.setGame(entity.getGame().getId());
//        questionDto.setAnswersSet(entity.getAnswersSet()
//                .stream()
//                .map(Answer::getId)
//                .collect(Collectors.toSet()));
        return questionDto;
    }

    @Override
    public Question toEntity(QuestionDto dto) {
//        Set<Answer> answers = dto.getAnswersSet()
//                .stream()
//                .map(answerService::getAnswerById)
//                .collect(Collectors.toSet());
        Category category = categoryService.findCategoryById(dto.getCategory());
        Level level = levelService.findLevelById(dto.getLevel());
        Game game = gameService.findGameById(dto.getGame());
        Question question = new Question();
        question.setId(dto.getId());
        question.setTitle(dto.getTitle());
        question.setDescription(dto.getDescription());
        question.setCategory(category);
        question.setLevel(level);
        question.setGame(game);
      //  question.setAnswersSet(answers);
        return question;
    }

    @Override
    public QuestionDto toShortDto(Question entity) {
        QuestionDto questionDto = new QuestionDto();
        questionDto.setId(entity.getId());
        questionDto.setTitle(entity.getTitle());
        questionDto.setDescription(entity.getDescription());
        questionDto.setCategory(entity.getCategory().getId());
        questionDto.setLevel(entity.getLevel().getId());
        questionDto.setAnswersSet(entity.getAnswersSet()
                .stream()
                .map(Answer::getId)
                .collect(Collectors.toSet()));
        return questionDto;
    }
}
