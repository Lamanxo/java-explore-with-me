package ru.practicum.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.practicum.enums.State;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventDto {
    Long id;
    String annotation;
    CategoryDto category;
    LocalDateTime eventDate;
    UserShortDto initiator;
    LocationDto location;
    Boolean paid;
    String title;
    Long confirmedRequests;
    LocalDateTime createdOn;
    String description;
    Long participantLimit;
    LocalDateTime publishedOn;
    Boolean requestModeration;
    State state;
    Long views;

}
