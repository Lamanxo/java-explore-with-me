package ru.practicum.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

import static ru.practicum.dto.DateTimePattern.DEFAULT_TIME_FORMAT;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventDtoOut {
    Long id;
    String annotation;
    CategoryDto category;
    @JsonFormat(pattern = DEFAULT_TIME_FORMAT)
    LocalDateTime eventDate;
    UserShortDto initiator;
    Boolean paid;
    String title;
    Long confirmedRequests;
    Long views;

}
