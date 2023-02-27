package ru.practicum.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.practicum.enums.State;
import ru.practicum.model.Location;

import java.time.LocalDateTime;

import static ru.practicum.dto.DateTimePattern.DEFAULT_TIME_FORMAT;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventDto {
    Long id;
    String annotation;
    CategoryDto category;
    @JsonFormat(pattern = DEFAULT_TIME_FORMAT)
    LocalDateTime eventDate;
    UserShortDto initiator;
    Location location;
    Boolean paid;
    String title;
    Long confirmedRequests;
    @JsonFormat(pattern = DEFAULT_TIME_FORMAT)
    LocalDateTime createdOn;
    String description;
    Long participantLimit;
    @JsonFormat(pattern = DEFAULT_TIME_FORMAT)
    LocalDateTime publishedOn;
    Boolean requestModeration;
    State state;
    Long views;

}
