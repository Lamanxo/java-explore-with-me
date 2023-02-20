package ru.practicum.stat.service;

import ru.practicum.stat.dto.HitDtoIn;
import ru.practicum.stat.dto.StatDtoOut;

import java.time.LocalDateTime;
import java.util.List;

public interface StatService {
    void addHit(HitDtoIn hitDtoIn);

    List<StatDtoOut> getStats(LocalDateTime start, LocalDateTime end, List<String> urls, Boolean unique);
}
