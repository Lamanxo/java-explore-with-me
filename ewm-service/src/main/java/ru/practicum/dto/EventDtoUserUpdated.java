package ru.practicum.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.practicum.enums.UserStateAction;
import ru.practicum.model.Location;

import java.time.LocalDateTime;

import static ru.practicum.dto.DateTimePattern.DEFAULT_TIME_FORMAT;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventDtoUserUpdated {

    String annotation;
    CategoryDto category;
    String description;
    @JsonFormat(pattern = DEFAULT_TIME_FORMAT)
    LocalDateTime eventDate;
    Location location;
    Boolean paid;
    Long participantLimit;
    Boolean requestModeration;
    UserStateAction stateAction;
    String title;
}