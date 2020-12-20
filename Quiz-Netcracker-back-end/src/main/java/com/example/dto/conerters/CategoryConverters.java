package com.example.dto.conerters;

import com.example.dto.CategoryDto;
import com.example.model.Category;

public class CategoryConverters {
    public static CategoryDto convert(Category category){
        CategoryDto categoryDto = null;
        if(category != null){
            categoryDto = new CategoryDto();
            categoryDto.setDescription(category.getDescription());
            categoryDto.setName(category.getName());
            categoryDto.setId(category.getId());
        }
        return categoryDto;
    }

    public static Category convert(CategoryDto categoryDto){
        Category category = null;
        if(categoryDto != null){
            category = new Category();
            category.setDescription(categoryDto.getDescription());
            category.setName(categoryDto.getName());
            category.setId(categoryDto.getId());
        }
        return category;
    }
}
