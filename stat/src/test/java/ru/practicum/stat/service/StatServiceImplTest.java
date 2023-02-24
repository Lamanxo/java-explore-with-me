package ru.practicum.stat.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.stat.dto.HitDtoIn;
import ru.practicum.stat.dto.StatDtoOut;
import ru.practicum.stat.repo.HItRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class StatServiceImplTest {

    @InjectMocks
    private StatServiceImpl statService;
    @Mock
    private HItRepository hItRepository;

    LocalDateTime dateTime = LocalDateTime.of(2022,12,12,13,13,56);
    HitDtoIn dtoIn = new HitDtoIn("ewm-main-service", "/events/1", "73.80.0.87", dateTime);
    StatDtoOut statDtoOut = new StatDtoOut("ewm-main-service", "/events/1", 1L);

    @Test
    void addHitVerifiedTest() {
        when(hItRepository.save(any())).thenReturn(any());
        statService.addHit(dtoIn);
        verify(hItRepository, times(1)).save(any());
    }

    @Test
    void getStatsWithNonUniqueStatTest() {
        List<StatDtoOut> viewStatsList = List.of(statDtoOut);
        when(hItRepository.getNonUniqueStat(any(), any(), any())).thenReturn(viewStatsList);

        List<StatDtoOut> actualViewStatsList = statService.getStats(dateTime, dateTime, List.of(), Boolean.FALSE);

        assertEquals(viewStatsList, actualViewStatsList);
    }

    @Test
    void getStatsWithUniqueStatTest() {
        List<StatDtoOut> viewStatsList = List.of(statDtoOut);
        when(hItRepository.getUniqueStat(any(), any(), any())).thenReturn(viewStatsList);

        List<StatDtoOut> actualViewStatsList = statService.getStats(dateTime, dateTime, List.of(), Boolean.TRUE);

        assertEquals(viewStatsList, actualViewStatsList);
    }
}
