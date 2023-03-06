package ru.practicum.stat.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.stat.dto.HitDtoIn;
import ru.practicum.stat.dto.StatDtoOut;
import ru.practicum.stat.service.StatService;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static ru.practicum.stat.dto.DateTimePattern.DEFAULT_TIME_FORMAT;

@Slf4j
@RestController
@RequiredArgsConstructor
public class StatController {
    private final StatService statService;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public void addHit(@RequestBody HitDtoIn hitDtoIn) {
        log.info("Added Hit: {}", hitDtoIn.getUri());
        statService.addHit(hitDtoIn);
    }

    @GetMapping("/stats")
    public Collection<StatDtoOut> getStats(@RequestParam @DateTimeFormat(pattern = DEFAULT_TIME_FORMAT) LocalDateTime start,
                                          @RequestParam @DateTimeFormat(pattern = DEFAULT_TIME_FORMAT) LocalDateTime end,
                                          @RequestParam List<String> uris,
                                          @RequestParam(defaultValue = "false") Boolean unique) {
        log.info("Get stats: start {}, end {}, uris {}", start, end, uris.size());
        return statService.getStats(start, end, uris, unique);
    }
}
