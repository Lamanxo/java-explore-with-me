package ru.practicum.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventDtoOut {
    Long id;
    String annotation;
    CategoryDto category;
    LocalDateTime eventDate;
    UserShortDto initiator;
    Boolean paid;
    String title;
    Long confirmedRequests;
    Long views;

}
