package com.example.controller;

import com.example.dto.CategoryDto;
import com.example.dto.conerters.CategoryConverters;
import com.example.model.Category;
import com.example.model.Question;
import com.example.service.CategoryService;
import com.example.service.QuestionService;
import com.example.service.impl.CategoryServiceImpl;
import com.example.wrapper.CollectionWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public List<CategoryDto> getCategories() {
        return categoryService.findAllCategory()
                .stream()
                .map(CategoryConverters::convert)
                .collect(Collectors.toList());
    }

    @GetMapping("/categories/{categoryId}")
    public CategoryDto getQuestions(@PathVariable UUID categoryId) {
        return CategoryConverters.convert(categoryService.findCategoryById(categoryId));
    }

    @PostMapping("/category")
    public CategoryDto createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        Category category = CategoryConverters.convert(categoryDto);
        Category savedCategory = categoryService.saveCategory(category);
        return CategoryConverters.convert(savedCategory);
    }

    @PutMapping("/category/{categoryId}")
    public CategoryDto updateCategory(@PathVariable UUID categoryId,
                                   @Valid @RequestBody CategoryDto categoryDto) {
        Category category = CategoryConverters.convert(categoryDto);
        Category updatedCategory = categoryService.updateCategory(categoryId, category);
        return CategoryConverters.convert(updatedCategory);
    }

    @DeleteMapping("/category/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable UUID categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok().build();
    }
}
