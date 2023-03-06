package ru.practicum.service.interfaces;

import org.springframework.data.domain.Pageable;
import ru.practicum.dto.CategoryDto;

import java.util.Collection;

public interface CategoryService {

    CategoryDto addCategory(CategoryDto categoryDto);

    void deleteCategory(Long id);

    CategoryDto updateCategory(Long id, CategoryDto categoryDto);

    Collection<CategoryDto> getCategories(Pageable pageable);

    CategoryDto getCategoryById(Long id);

}
