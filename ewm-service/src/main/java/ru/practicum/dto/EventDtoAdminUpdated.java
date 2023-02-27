package ru.practicum.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.practicum.model.Location;

import java.time.LocalDateTime;

import static ru.practicum.dto.DateTimePattern.DEFAULT_TIME_FORMAT;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventDtoAdminUpdated {
    String annotation;
    Long category;
    String description;
    @JsonFormat(pattern = DEFAULT_TIME_FORMAT)
    LocalDateTime eventDate;
    Location location;
    Boolean paid;
    Long participantLimit;
    Boolean requestModeration;

    String title;


}
