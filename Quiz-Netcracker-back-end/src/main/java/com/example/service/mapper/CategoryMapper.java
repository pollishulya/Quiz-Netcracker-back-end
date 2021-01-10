package com.example.service.mapper;

import com.example.dto.*;
import com.example.model.*;
import com.example.service.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CategoryMapper implements Mapper<Category, CategoryDto> {
    private final QuestionService questionService;

    @Autowired
    public CategoryMapper(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Override
    public CategoryDto toDto(Category entity) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(entity.getId());
        categoryDto.setName(entity.getName());
        categoryDto.setDescription(entity.getDescription());
        categoryDto.setQuestion(entity.getQuestion()
                .stream()
                .map(Question::getId)
                .collect(Collectors.toSet()));
        return categoryDto;
    }

    @Override
    public Category toEntity(CategoryDto dto) {
        Set<Question> questions = dto.getQuestion()
                .stream()
                .map(questionService::getQuestionById)
                .collect(Collectors.toSet());
        Category category = new Category();
        category.setId(dto.getId());
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        category.setQuestion(questions);
        return category;
    }

    @Override
    public CategoryDto toShortDto(Category entity) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName(entity.getName());
        return categoryDto;
    }
}
