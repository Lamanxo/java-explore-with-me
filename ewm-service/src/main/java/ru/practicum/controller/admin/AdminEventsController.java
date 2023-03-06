package ru.practicum.controller.admin;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.EventDto;
import ru.practicum.dto.EventDtoAdminUpdated;
import ru.practicum.enums.State;
import ru.practicum.service.interfaces.EventService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.practicum.dto.DateTimePattern.DEFAULT_TIME_FORMAT;

@RestController
@RequestMapping("/admin/events")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdminEventsController {

    final EventService service;

    @PatchMapping("/{eventId}")
    public EventDto updateEventByAdmin(@PathVariable Long eventId,
                                       @RequestBody EventDtoAdminUpdated eventDtoAdminUpdated) {
        log.info("Update event {} by admin endpoint", eventId);
        return service.updateEventByAdmin(eventId, eventDtoAdminUpdated);
    }

    @GetMapping
    public Collection<EventDto> getAllByAdmin(@RequestParam(defaultValue = "") Set<Long> users,
                                              @RequestParam(defaultValue = "") Set<String> states,
                                              @RequestParam(defaultValue = "") Set<Long> categories,
                                              @RequestParam(defaultValue = "") @DateTimeFormat(pattern = DEFAULT_TIME_FORMAT) LocalDateTime rangeStart,
                                              @RequestParam(defaultValue = "") @DateTimeFormat(pattern = DEFAULT_TIME_FORMAT) LocalDateTime rangeEnd,
                                              @PositiveOrZero @RequestParam(defaultValue = "0") int from,
                                              @Positive @RequestParam(defaultValue = "10") int size) {
        log.info("Get all events by admin dates from {} to {}", rangeStart, rangeEnd);
        return service.getAllByAdmin(new ArrayList<>(users), states.stream().map(State::valueOf).collect(Collectors.toList()),
                new ArrayList<>(categories), rangeStart, rangeEnd, PageRequest.of(from, size));
    }


}
