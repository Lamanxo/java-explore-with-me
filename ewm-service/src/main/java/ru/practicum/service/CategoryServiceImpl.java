package ru.practicum.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.CategoryDto;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mappers.CategoryMapper;
import ru.practicum.model.Category;
import ru.practicum.repo.CategoryRepository;
import ru.practicum.service.interfaces.CategoryService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryServiceImpl implements CategoryService {

    final CategoryRepository catRepo;

    @Override
    @Transactional
    public CategoryDto addCategory(CategoryDto categoryDto) {
        Category category;
        try {
            category = catRepo.save(CategoryMapper.makeCategory(categoryDto));
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("This category name already used ");
        }
        return CategoryMapper.makeCategoryDto(category);
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        getCategoryById(id);
        catRepo.deleteById(id);
    }

    @Override
    @Transactional
    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        Category category = catRepo.findById(id).orElseThrow(() ->
                new NotFoundException("Category with id: " + id + " not found"));
        category.setName(categoryDto.getName());
        return CategoryMapper.makeCategoryDto(catRepo.save(category));
    }

    @Override
    public Collection<CategoryDto> getCategories(Pageable pageable) {
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        for (var category : catRepo.findAll(pageable)) {
            categoryDtoList.add(CategoryMapper.makeCategoryDto(category));
        }
        return categoryDtoList;
    }

    @Override
    public CategoryDto getCategoryById(Long id) {
        return CategoryMapper.makeCategoryDto(catRepo.findById(id).orElseThrow(() ->
                new NotFoundException("Category with id: " + id + " not found")));
    }
}
