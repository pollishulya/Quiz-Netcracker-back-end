package com.example.service.mapper;

import com.example.dto.*;
import com.example.model.*;
import com.example.service.interfaces.MapperService;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class Mapper implements MapperService {

    @Override
    public AnswerDto toAnswerDto(Answer answer) {
        return AnswerDto.builder()
                .id(answer.getId())
                .title(answer.getTitle())
                .right(answer.getRight())
                .question(answer.getQuestion())
                .build();
    }

    @Override
    public Answer fromAnswerDto(AnswerDto answerDto) {
        return Answer.builder()
                .id(answerDto.getId())
                .title(answerDto.getTitle())
                .right(answerDto.getRight())
                .question(answerDto.getQuestion())
                .build();
    }

    @Override
    public AnswerDto toShortAnswerDto(Answer answer) {
        return AnswerDto.builder()
                .title(answer.getTitle())
                .question(answer.getQuestion())
                .build();
    }

    @Override
    public GameDto toGameDto(Game game) {
        return GameDto.builder()
                .id(game.getId())
                .description(game.getDescription())
                .name(game.getName())
                .user(game.getUser())
                .build();
    }

    @Override
    public Game fromGameDto(GameDto gameDto) {
        return Game.builder()
                .id(gameDto.getId())
                .description(gameDto.getDescription())
                .name(gameDto.getName())
                .user(gameDto.getUser())
                .build();
    }

    @Override
    public GameDto toShortGameDto(Game game) {
        return GameDto.builder()
                .name(game.getName())
                .description(game.getDescription())
                .build();
    }

    @Override
    public LevelDto toLevelDto(Level level) {
        return LevelDto.builder()
                .id(level.getId())
                .description(level.getDescription())
                .title(level.getTitle())
                .questions(level.getQuestions())
                .build();
    }

    @Override
    public Level fromLevelDto(LevelDto levelDto) {
        return Level.builder()
                .id(levelDto.getId())
                .description(levelDto.getDescription())
                .title(levelDto.getTitle())
                .questions(levelDto.getQuestions())
                .build();
    }

    @Override
    public LevelDto toShortLevelDto(Level level) {
        return LevelDto.builder()
                .description(level.getDescription())
                .title(level.getTitle())
                .build();
    }

    @Override
    public RoleDto toRoleDto(Role role) {
        return RoleDto.builder()
                .id(role.getId())
                .title(role.getTitle())
                .users(role.getUserSet())
                .build();
    }

    @Override
    public Role fromRoleDto(RoleDto roleDto) {
        return Role.builder()
                .id(roleDto.getId())
                .title(roleDto.getTitle())
                .userSet(roleDto.getUsers())
                .build();
    }

    @Override
    public RoleDto toShortRoleDto(Role role) {
        return RoleDto.builder()
                .title(role.getTitle())
                .build();
    }

    @Override
    public UserDto toUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .mail(user.getMail())
                .login(user.getLogin())
                .password(user.getPassword())
                .roles(user.getRole())
                .games(user.getGame())
                .build();
    }

    @Override
    public User fromUserDto(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .mail(userDto.getMail())
                .login(userDto.getLogin())
                .password(userDto.getPassword())
                .role(userDto.getPassword())
                .game(userDto.getGames())
                .build();
    }

    @Override
    public UserDto toShortDto(User user) {
        return UserDto.builder()
                .login(user.getLogin())
                .roles(user.getRole())
                .build();
    }

    @Override
    public QuestionDto toQuestionDto(Question question) {
        return QuestionDto.builder()
                .id(question.getId())
                .title(question.getTitle())
                .description(question.getDescription())
                .category(question.getCategory())
                .level(question.getLevel())
                .answersSet(question.getAnswersSet()
                    .stream()
                    .map(this::toAnswerDto)
                    .collect(Collectors.toSet()))
                .build();
    }

    @Override
    public Question fromQuestionDto(QuestionDto questionDto) {
        return Question.builder()
                .id(questionDto.getId())
                .title(questionDto.getTitle())
                .description(questionDto.getDescription())
                .category(questionDto.getCategory())
                .level(questionDto.getLevel())
                .game(questionDto.getGame())
                .answersSet(questionDto.getAnswersSet()
                    .stream()
                    .map(this::fromAnswerDto)
                    .collect(Collectors.toSet()))
                .build();
    }

    @Override
    public QuestionDto toShortQuestionDto(Question question) {
        return QuestionDto.builder()
                .id(question.getId())
                .title(question.getTitle())
                .description(question.getDescription())
                .category(question.getCategory())
                .level(question.getLevel())
                .answersSet(question.getAnswersSet()
                    .stream()
                    .map(this::toShortAnswerDto)
                    .collect(Collectors.toSet()))
                .build();
    }

    @Override
    public CategoryDto toCategoryDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .question(category.getQuestion())
                .build();
    }

    @Override
    public CategoryDto toShortCategoryDto(Category category) {
        return CategoryDto.builder()
                .name(category.getName())
                .build();
    }

    @Override
    public Category fromCategoryDro(CategoryDto categoryDto) {
        return Category.builder()
                .id(categoryDto.getId())
                .name(categoryDto.getName())
                .description(categoryDto.getDescription())
                .question(categoryDto.getQuestion())
                .build();
    }
}
