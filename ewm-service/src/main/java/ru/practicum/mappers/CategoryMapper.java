package ru.practicum.mappers;

import ru.practicum.dto.CategoryDto;
import ru.practicum.model.Category;

public class CategoryMapper {

    public static Category makeCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setName(category.getName());
        return category;
    }

    public static CategoryDto makeCategoryDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        return categoryDto;
    }
}
