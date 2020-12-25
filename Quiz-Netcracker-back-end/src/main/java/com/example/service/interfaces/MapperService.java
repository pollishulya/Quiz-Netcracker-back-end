package com.example.service.interfaces;

import com.example.dto.*;
import com.example.model.*;

public interface MapperService {
    AnswerDto toAnswerDto(final Answer answer);
    Answer fromAnswerDto(final AnswerDto answerDto);
    AnswerDto toShortAnswerDto(final Answer answer);
    GameDto toGameDto(final Game game);
    Game fromGameDto(final GameDto gameDto);
    GameDto toShortGameDto(final Game game);
    LevelDto toLevelDto(final Level level);
    Level fromLevelDto(final LevelDto levelDto);
    LevelDto toShortLevelDto(final Level level);
    RoleDto toRoleDto(final Role role);
    Role fromRoleDto(final RoleDto roleDto);
    RoleDto toShortRoleDto(final Role role );
    UserDto toUserDto(final User user);
    User fromUserDto(final UserDto userDto);
    UserDto toShortDto(final User user);
    QuestionDto toQuestionDto(final Question question);
    Question fromQuestionDto(final QuestionDto questionDto);
    QuestionDto toShortQuestionDto(final Question question);
    CategoryDto toCategoryDto(final Category category);
    CategoryDto toShortCategoryDto(final Category category);
    Category fromCategoryDro(final CategoryDto categoryDto);
}
