package ru.practicum.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RequestStatusDtoOut {
    List<PartyRequestDto> confirmedRequests;
    List<PartyRequestDto> rejectedRequests;
}
