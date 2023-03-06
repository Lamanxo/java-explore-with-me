package ru.practicum.controller.other;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.EventDto;
import ru.practicum.dto.EventDtoOut;
import ru.practicum.service.interfaces.EventService;
import ru.practicum.service.interfaces.StatService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static ru.practicum.dto.DateTimePattern.DEFAULT_TIME_FORMAT;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PublicEventsController {

    final EventService service;
    final StatService statService;

    @GetMapping("/{id}")
    public EventDto getByIdPublic(@PathVariable Long id, HttpServletRequest request) {
        statService.hitEndpoint(request.getRequestURI(), request.getRemoteAddr());
        log.info("Get event by public ID: {}", id);
        return service.getByIdPublic(id);
    }

    @GetMapping
    public Collection<EventDtoOut> getAllByPublic(@RequestParam(required = false) String text,
                                                  @RequestParam(required = false) List<Long> categories,
                                                  @RequestParam(required = false) Boolean paid,
                                                  @RequestParam(required = false) @DateTimeFormat(pattern = DEFAULT_TIME_FORMAT) LocalDateTime rangeStart,
                                                  @RequestParam(required = false) @FutureOrPresent @DateTimeFormat(pattern = DEFAULT_TIME_FORMAT) LocalDateTime rangeEnd,
                                                  @RequestParam(defaultValue = "false") boolean onlyAvailable,
                                                  @RequestParam(required = false) String sort,
                                                  @PositiveOrZero @RequestParam(defaultValue = "0") int from,
                                                  @Positive @RequestParam(defaultValue = "10") int size,
                                                  HttpServletRequest request) {
        statService.hitEndpoint(request.getRequestURI(), request.getRemoteAddr());
        Pageable pageable = PageRequest.of(from, size);
        if (sort != null) {
            Sort sorting;
            switch (sort) {
                case "EVENT_DATE":
                    sorting = Sort.by(Sort.Direction.DESC, "eventDate");
                    break;
                case "VIEWS":
                    sorting = Sort.by(Sort.Direction.DESC, "views");
                    break;
                default:
                    sorting = Sort.by(Sort.Direction.DESC, "id");
            }
            pageable = PageRequest.of(from, size, sorting);

        }
        log.info("Get all events by public endpoint. Text {}", text);
        return service.getAllByPublic(text, categories, paid, rangeStart,
                rangeEnd, onlyAvailable, pageable);
    }

}
