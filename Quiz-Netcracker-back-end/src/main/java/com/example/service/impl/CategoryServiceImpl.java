package com.example.service.impl;

import com.example.exception.ResourceNotFoundException;
import com.example.model.Category;
import com.example.repository.CategoryRepository;
import com.example.service.interfaces.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category findCategoryById(UUID id) {
        return categoryRepository.getCategoryById(id);
    }

    @Override
    public List<Category> findAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(UUID categoryId, Category categoryRequest) {
        return categoryRepository.findById(categoryId).map(category -> {
            category.setDescription(categoryRequest.getDescription());
            category.setName(categoryRequest.getName());
            return categoryRepository.save(category);
        }).orElseThrow(() -> new ResourceNotFoundException("Question not found with id " + categoryId));
    }

    @Override
    public void deleteCategory(UUID categoryId) {
        categoryRepository.deleteById(categoryId);
    }


}
