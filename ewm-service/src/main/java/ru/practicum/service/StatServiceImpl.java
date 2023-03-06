package ru.practicum.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.practicum.client.StatClient;
import ru.practicum.dto.HitEndpointDto;
import ru.practicum.service.interfaces.StatService;

import java.time.LocalDateTime;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class StatServiceImpl implements StatService {

    static final String APP_NAME = "ewm-service";
    final StatClient client;

    @Override
    public void hitEndpoint(String uri, String ip) {
        HitEndpointDto endpointHit = new HitEndpointDto(APP_NAME, uri, ip, LocalDateTime.now());
        client.addToStat(endpointHit);
    }
}
