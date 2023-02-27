package ru.practicum.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.practicum.enums.UserStateAction;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventDtoUserUpdated {

    String annotation;
    CategoryDto category;
    String description;
    LocalDateTime eventDate;
    LocationDto location;
    Boolean paid;
    Long participantLimit;
    Boolean requestModeration;
    UserStateAction stateAction;
    String title;
}