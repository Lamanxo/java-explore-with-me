package ru.practicum.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;
import ru.practicum.model.Location;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static ru.practicum.dto.DateTimePattern.DEFAULT_TIME_FORMAT;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventDtoIn {
    @Length(min = 20, max = 2048)
    String annotation;
    @NotNull
    Long category;
    @Length(min = 20, max = 7000)
    String description;
    @NotBlank
    @JsonFormat(pattern = DEFAULT_TIME_FORMAT)
    LocalDateTime eventDate;
    @NotNull
    Location location;
    @Length(min = 3, max = 128)
    String title;
    Boolean paid;
    Long participantLimit;
    Boolean requestModeration;
}

