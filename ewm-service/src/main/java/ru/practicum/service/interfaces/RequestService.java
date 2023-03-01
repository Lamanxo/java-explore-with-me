package ru.practicum.service.interfaces;

import ru.practicum.dto.PartyRequestDto;

import java.util.Collection;

public interface RequestService {

    PartyRequestDto addRequest(Long userId, Long eventId);

    PartyRequestDto cancelRequest(Long userId, Long requestId);

    Collection<PartyRequestDto> getAllUserRequests(Long userId);

}
