package ru.practicum.stat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.practicum.stat.dto.HitDtoIn;
import ru.practicum.stat.dto.StatDtoOut;
import ru.practicum.stat.service.StatService;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = StatController.class)
public class StatControllerTest {

    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    StatService statService;
    @Autowired
    MockMvc mockMvc;

    @SneakyThrows
    @Test
    void addHitTest() {
        HitDtoIn hitDto = new HitDtoIn("ewm-main-service", "/events/32", "10.95.55.13",
                LocalDateTime.of(2022,12,12,13,13,56));

        mockMvc.perform(MockMvcRequestBuilders.post("/hit")
                        .content(objectMapper.writeValueAsString(hitDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));
    }

    @SneakyThrows
    @Test
    void getStatsTest() {
        StatDtoOut viewStats = new StatDtoOut("ewm-main-service", "/events/33", 1L);
        when(statService.getStats(any(), any(), any(), any())).thenReturn(List.of(viewStats));

        mockMvc.perform(get("/stats")
                        .param("start", "2022-12-13 13:00:00")
                        .param("end", "2022-12-14 16:59:59")
                        .param("uris", List.of().toString())
                        .param("unique", "FALSE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].app", is(viewStats.getApp())))
                .andExpect(jsonPath("$[0].uri", is(viewStats.getUri())))
                .andExpect(jsonPath("$[0].hits", is(1)));
    }
}
