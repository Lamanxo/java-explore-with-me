package ru.practicum.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    Long id;
    @NotBlank
    String name;
}
