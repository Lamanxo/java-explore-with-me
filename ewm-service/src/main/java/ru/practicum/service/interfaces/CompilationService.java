package ru.practicum.service.interfaces;

import org.springframework.data.domain.Pageable;
import ru.practicum.dto.CompilationDto;
import ru.practicum.dto.CompilationDtoIn;

import java.util.List;

public interface CompilationService {

    CompilationDto addCompilation(CompilationDtoIn dtoIn);

    CompilationDto updateCompilation(Long id, CompilationDtoIn dtoIn);

    CompilationDto getCompilationById(Long id);

    void deleteCompilation(Long id);

    List<CompilationDto> getCompilations(Pageable pageable, Boolean pinned);

}
