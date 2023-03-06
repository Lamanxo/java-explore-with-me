package ru.practicum.controller.likes;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.dto.EventDtoOut;
import ru.practicum.dto.UserShortDto;
import ru.practicum.service.interfaces.LikesService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Collection;

@RestController
@RequestMapping("/raiting")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PublicLikeController {
    final LikesService service;

    @GetMapping("/users")
    public Collection<UserShortDto> getPopularUsers(
            @PositiveOrZero @RequestParam(defaultValue = "0") int from,
            @Positive @RequestParam(defaultValue = "20") int size) {
        log.info("public get popular users, param: from: {}, size: {}", from, size);
        return service.getPopularUsers(PageRequest.of(from, size));
    }

    @GetMapping("/events")
    public Collection<EventDtoOut> getPopularEvents(@PositiveOrZero @RequestParam(defaultValue = "0") int from,
                                                    @Positive @RequestParam(defaultValue = "20") int size) {
        log.info("public get popular events, param: from: {}, size: {}", from, size);
        return service.getPopularEvents(PageRequest.of(from, size));
    }
}
