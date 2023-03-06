package ru.practicum.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import static ru.practicum.dto.DateTimePattern.DEFAULT_TIME_FORMAT;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HitEndpointDto {
    private String app;
    private String uri;
    private String ip;
    @JsonFormat(pattern = DEFAULT_TIME_FORMAT)
    private LocalDateTime timestamp;
}
