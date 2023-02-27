package ru.practicum.service.interfaces;

import org.springframework.data.domain.Pageable;
import ru.practicum.dto.*;
import ru.practicum.enums.State;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {

    EventDto addByUser(Long userId, EventDtoIn eventDtoIn);

    EventDto getByIdPublic(Long id);

    EventDto updateEventByUser(Long userId, Long eventId, EventDtoUserUpdated dtoUserUpdated);

    EventDto updateEventByAdmin(Long eventId, EventDtoAdminUpdated dtoAdminUpdated);

    List<EventDtoOut> getAllByPublic(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart,
                                     LocalDateTime rangeEnd, Boolean onlyAvailable, String sort, Pageable pageable);

    List<EventDto> getAllByAdmin(List<Long> users, List<State> states, List<Long> categories,
                                 LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                 Pageable pageable);
}
