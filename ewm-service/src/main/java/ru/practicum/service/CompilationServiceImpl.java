package ru.practicum.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.CompilationDto;
import ru.practicum.dto.CompilationDtoIn;
import ru.practicum.exception.NotFoundException;
import ru.practicum.model.Compilation;
import ru.practicum.model.Event;
import ru.practicum.repo.CompilationRepository;
import ru.practicum.repo.EventRepository;
import ru.practicum.service.interfaces.CompilationService;

import java.util.ArrayList;
import java.util.List;

import static ru.practicum.mappers.CompilationMapper.makeCompilation;
import static ru.practicum.mappers.CompilationMapper.makeCompilationDto;

@RequiredArgsConstructor
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompilationServiceImpl implements CompilationService {

    final CompilationRepository compRepo;
    final EventRepository eventRepo;

    @Override
    @Transactional
    public CompilationDto addCompilation(CompilationDtoIn dtoIn) {
        List<Event> events = new ArrayList<>();
        for (var event : dtoIn.getEvents()) {
            events.add(eventOrException(event));
        }
        return makeCompilationDto(compRepo.save(makeCompilation(dtoIn,events)));
    }

    @Override
    @Transactional
    public CompilationDto updateCompilation(Long id, CompilationDtoIn dtoIn) {
        Compilation compilation = compilationOrException(id);
        if (dtoIn.getPinned() != null) {
            compilation.setPinned(dtoIn.getPinned());
        }
        if (dtoIn.getTitle() != null) {
            compilation.setTitle(dtoIn.getTitle());
        }
        List<Event> eventsList = eventRepo.findAllByIdIn(dtoIn.getEvents());
        compilation.setEvents(eventsList);
        return makeCompilationDto(compRepo.save(compilation));
    }

    @Override
    public CompilationDto getCompilationById(Long id) {
        return makeCompilationDto(compilationOrException(id));
    }

    @Override
    @Transactional
    public void deleteCompilation(Long id) {
        compilationOrException(id);
        compRepo.deleteById(id);
    }

    @Override
    public List<CompilationDto> getCompilations(Pageable pageable, Boolean pinned) {
        List<CompilationDto> dtoList = new ArrayList<>();
        if (pinned != null) {
            for (var comp : compRepo.findAllByPinned(pinned,pageable)) {
                dtoList.add(makeCompilationDto(comp));
            }
        } else {
            for (var comp : compRepo.findAll(pageable)) {
                dtoList.add(makeCompilationDto(comp));
            }
        }
        return dtoList;
    }

    private Compilation compilationOrException(Long id) {
        return compRepo.findById(id).orElseThrow(() ->
                new NotFoundException("Compilation " + id + " not found"));
    }

    private Event eventOrException(Long eventId) {
        return eventRepo.findById(eventId).orElseThrow(() ->
                new NotFoundException("Event with id: " + eventId + " not found"));
    }
}
