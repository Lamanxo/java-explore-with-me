package ru.practicum.mappers;

import ru.practicum.dto.PartyRequestDto;
import ru.practicum.model.Request;

public class RequestMapper {

    public static PartyRequestDto makeRequestDto(Request request) {
        PartyRequestDto dto = new PartyRequestDto();
        dto.setId(request.getId());
        dto.setCreated(request.getCreated());
        dto.setEvent(request.getEvent().getId());
        dto.setRequester(request.getRequester().getId());
        dto.setStatus(request.getStatus());
        return dto;
    }

}
