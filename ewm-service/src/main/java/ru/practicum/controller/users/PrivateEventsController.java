package ru.practicum.controller.users;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.*;
import ru.practicum.service.interfaces.EventService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Collection;

@RestController
@RequestMapping("/users/{userId}/events")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class PrivateEventsController {

    final EventService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventDto addByUser(@PathVariable Long userId,
                              @RequestBody EventDtoIn eventDtoIn) {
        log.info("User with id: {} created event: {}", userId, eventDtoIn);
        return service.addByUser(userId, eventDtoIn);
    }

    @GetMapping("{eventId}")
    public EventDto getByUser(@PathVariable Long userId,
                              @PathVariable Long eventId) {
        log.info("Get event {} by user {} endpoint", eventId, userId);
        return service.getByUser(userId, eventId);
    }

    @GetMapping
    public Collection<EventDtoOut> getAllByUser(@PathVariable Long userId,
                                                  @PositiveOrZero @RequestParam(defaultValue = "0") int from,
                                                  @Positive @RequestParam(defaultValue = "10") int size) {
        log.info("Get all events of user {}", userId);
        return service.getAllByUser(userId, PageRequest.of(from, size));
    }

    @PatchMapping("{eventId}")
    public EventDto updateEventByUser(@PathVariable Long userId, @PathVariable Long eventId,
                                      @RequestBody @Valid EventDtoUserUpdated eventDtoUserUpdated) {
        log.info("Update event {} by user {}", eventId, userId);
        return service.updateEventByUser(userId, eventId, eventDtoUserUpdated);
    }

    @GetMapping("{eventId}/requests")
    public Collection<PartyRequestDto> getRequestsOfEvent(@PathVariable Long userId,
                                                          @PathVariable Long eventId) {
        log.info("Getting requests of event {} by user {}", eventId, userId);
        return service.getUserOwnRequests(userId, eventId);
    }

    @PatchMapping("{eventId}/requests")
    public RequestStatusDtoOut updateRequest(@PathVariable() Long userId,
                                             @PathVariable() Long eventId,
                                             @RequestBody() RequestStatusDtoIn dtoIn) {
        log.info("User with id: {} update requests to event with id: {} to {}", userId, eventId, dtoIn);
        return service.updateRequest(userId, eventId, dtoIn);
    }

}
