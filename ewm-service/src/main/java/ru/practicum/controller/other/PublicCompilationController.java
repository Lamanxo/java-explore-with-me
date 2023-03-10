package ru.practicum.controller.other;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.CompilationDto;
import ru.practicum.service.interfaces.CompilationService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Collection;

@RestController
@RequestMapping("/compilations")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class PublicCompilationController {
    final CompilationService service;

    @GetMapping
    public Collection<CompilationDto> getCompilations(@RequestParam(required = false) Boolean pinned,
                                                      @PositiveOrZero @RequestParam(defaultValue = "0") int from,
                                                      @Positive @RequestParam(defaultValue = "10") int size) {
        log.info("Get compilations buy public endpoint. Pinned {}, from {}, size {}", pinned, from, size);
        return service.getCompilations(PageRequest.of(from, size), pinned);
    }

    @GetMapping("/{id}")
    public CompilationDto getCompilationById(@PathVariable Long id) {
        log.info("Get compilation by public ID {}", id);
        return service.getCompilationById(id);
    }
}
