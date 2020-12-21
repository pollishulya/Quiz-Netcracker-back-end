package com.example.service;

import com.example.dto.CategoryDto;
import com.example.model.Category;
import com.example.model.Question;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    Category findCategoryById (UUID id);
    List<Category> findAllCategory();
    Category saveCategory(Category category);
    Category updateCategory(UUID categoryId, Category categoryRequest);
    void deleteCategory(UUID categoryId);
}
