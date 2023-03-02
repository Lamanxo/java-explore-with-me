package ru.practicum.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.practicum.dto.HitEndpointDto;

@FeignClient(value = "stat", url = "${feign.url}")
public interface StatClient {

    @PostMapping("/hit")
    void addToStat(@RequestBody HitEndpointDto endpointHitInputDto);
}
