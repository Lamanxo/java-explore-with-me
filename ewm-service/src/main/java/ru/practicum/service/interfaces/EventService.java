package ru.practicum.service.interfaces;

import org.springframework.data.domain.Pageable;
import ru.practicum.dto.*;
import ru.practicum.enums.State;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface EventService {

    EventDto addByUser(Long userId, EventDtoIn eventDtoIn);

    List<EventDtoOut> getAllByUser(Long userId, Pageable pageable);

    EventDto updateEventByUser(Long userId, Long eventId, EventDtoUserUpdated dtoUserUpdated);

    EventDto getByUser(Long userId, Long eventId);

    EventDto getByIdPublic(Long id);

    List<EventDtoOut> getAllByPublic(String text, List<Long> categories,
                                     Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                     Boolean onlyAvailable, Pageable pageable);

    EventDto updateEventByAdmin(Long eventId, EventDtoAdminUpdated dtoAdminUpdated);

    List<EventDto> getAllByAdmin(List<Long> users, List<State> states, List<Long> categories,
                                 LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                 Pageable pageable);

    Collection<PartyRequestDto> getUserOwnRequests(Long userId, Long eventId);

    RequestStatusDtoOut updateRequest(Long userId, Long eventId, RequestStatusDtoIn dtoIn);

}
