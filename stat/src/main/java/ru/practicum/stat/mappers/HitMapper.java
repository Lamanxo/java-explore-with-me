package ru.practicum.stat.mappers;

import ru.practicum.stat.dto.HitDtoIn;
import ru.practicum.stat.model.Hit;

public class HitMapper {

    public static Hit makeHit(HitDtoIn dtoIn) {
        Hit hit = new Hit(dtoIn.getApp(),dtoIn.getUri(),dtoIn.getIp(),dtoIn.getTimestamp());
        return hit;
    }
}
