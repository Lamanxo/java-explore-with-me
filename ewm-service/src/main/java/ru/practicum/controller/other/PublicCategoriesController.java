package ru.practicum.controller.other;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/categories")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class PublicCategoriesController {

    final CategoryService service;

    @GetMapping
    public Collection<CategoryDto> getCategories(@PositiveOrZero @RequestParam (defaultValue = "0") Integer from,
                                                 @Positive @RequestParam (defaultValue = "20") Integer size) {
        log.info("Get categories by public endpoint. from {}, size {}", from, size);
        Pageable pageable = PageRequest.of(from,size);
        return service.getCategories(pageable);
    }

    @GetMapping("/{id}")
    public CategoryDto getCategory(@PathVariable Long id) {
        log.info("Get category by public endpoint ID: {}", id);
        return service.getCategoryById(id);
    }

}
