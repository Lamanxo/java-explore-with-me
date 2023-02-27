package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.CategoryDto;
import ru.practicum.service.interfaces.CategoryService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public/categories")
public class PublicCategoriesController {

    private final CategoryService service;

    @GetMapping
    public Collection<CategoryDto> getCategories(@PositiveOrZero @RequestParam (defaultValue = "0") Integer from,
                                                 @Positive @RequestParam (defaultValue = "20") Integer size) {
        Pageable pageable = PageRequest.of(from,size);
        return service.getCategories(pageable);
    }

    @GetMapping("/{id}")
    public CategoryDto getCategory(@PathVariable Long id) {
        return service.getCategoryById(id);
    }

}
