package ru.practicum.mappers;

import ru.practicum.dto.CompilationDto;
import ru.practicum.dto.CompilationDtoIn;
import ru.practicum.dto.EventDtoOut;
import ru.practicum.model.Compilation;
import ru.practicum.model.Event;

import java.util.ArrayList;
import java.util.List;

public class CompilationMapper {

    public static CompilationDto makeCompilationDto(Compilation compilation) {
        CompilationDto dto = new CompilationDto();
        dto.setId(compilation.getId());
        dto.setTitle(compilation.getTitle());
        dto.setPinned(compilation.getPinned());
        List<EventDtoOut> dtoOut = new ArrayList<>();
        for (var event : compilation.getEvents()) {
            dtoOut.add(EventMapper.makeEventDtoOut(event));
        }
        dto.setEvents(dtoOut);
        return dto;
    }

    public static Compilation makeCompilation(CompilationDtoIn dtoIn, List<Event> events) {
        Compilation compilation = new Compilation();
        compilation.setTitle(dtoIn.getTitle());
        compilation.setPinned(dtoIn.getPinned());
        compilation.setEvents(events);
        return compilation;
    }

}
