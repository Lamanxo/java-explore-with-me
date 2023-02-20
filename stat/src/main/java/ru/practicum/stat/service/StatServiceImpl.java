package ru.practicum.stat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.stat.dto.HitDtoIn;
import ru.practicum.stat.dto.StatDtoOut;
import ru.practicum.stat.mappers.HitMapper;
import ru.practicum.stat.repo.HitRepo;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatServiceImpl implements StatService{

    private final HitRepo hitRepo;

    @Override
    public void addHit(HitDtoIn hitDtoIn) {
        hitRepo.save(HitMapper.makeHit(hitDtoIn));
    }

    @Override
    public List<StatDtoOut> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        if (unique) {
            return hitRepo.getUniqueStat(start, end, uris);
        } else {
            return hitRepo.getNonUniqueStat(start, end, uris);
        }
    }


}
