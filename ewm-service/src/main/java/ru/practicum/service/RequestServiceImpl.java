package ru.practicum.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.PartyRequestDto;
import static ru.practicum.mappers.RequestMapper.*;

import ru.practicum.enums.State;
import ru.practicum.enums.Status;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.model.Event;
import ru.practicum.model.Request;
import ru.practicum.model.User;
import ru.practicum.repo.EventRepository;
import ru.practicum.repo.RequestRepository;
import ru.practicum.repo.UserRepository;
import ru.practicum.service.interfaces.RequestService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RequestServiceImpl implements RequestService {

    final EventRepository eventRepo;
    final UserRepository userRepo;
    final RequestRepository requestRepo;

    @Override
    @Transactional
    public PartyRequestDto addRequest(Long userId, Long eventId) {
        User requester = userOrException(userId);
        Event event = eventOrException(eventId);
        if (event.getInitiator().getId().equals(userId)) {
            throw new ConflictException("User are initiator");
        }
        if (event.getState() != State.PUBLISHED) {
            throw new ConflictException("Event not posted yet");
        }
        List<Request> requests = requestRepo.findAllByRequesterIdAndEventId(userId,eventId);
        if (requests.size() != 0) {
            throw new ConflictException("User already sent request");
        }
        if (event.getParticipantLimit() != 0 && event.getParticipantLimit() <= requestRepo.countAllByEventId(eventId)) {
            throw new ConflictException("Event already full");
        }
        Request request = makeFullRequest(event, requester);
        return makeRequestDto(requestRepo.save(request));
    }

    @Override
    @Transactional
    public PartyRequestDto cancelRequest(Long userId, Long requestId) {
        Request request = requestOrException(requestId);
        userOrException(userId);
        if (!request.getRequester().getId().equals(userId)) {
            throw new ConflictException("Requester cant cancel others request");
        }
        request.setStatus(Status.CANCELED);
        return makeRequestDto(requestRepo.save(request));
    }

    @Override
    public Collection<PartyRequestDto> getAllUserRequests(Long userId) {
        userOrException(userId);
        List<PartyRequestDto> dtoList = new ArrayList<>();
        for (Request request : requestRepo.findAllByRequesterId(userId)) {
            dtoList.add(makeRequestDto(request));
        }
        return dtoList;
    }

    private Request makeFullRequest(Event event, User user) {
        Request request = new Request();
        request.setCreated(LocalDateTime.now());
        request.setEvent(event);
        request.setRequester(user);
        request.setStatus(event.getRequestModeration() ? Status.PENDING : Status.CONFIRMED);
        return request;
    }

    private User userOrException(Long userId) {
        return userRepo.findById(userId).orElseThrow(() ->
                new NotFoundException("User " + userId + " not found"));
    }

    private Event eventOrException(Long eventId) {
        return eventRepo.findById(eventId).orElseThrow(() ->
                new NotFoundException("Event " + eventId + " not found"));
    }

    private Request requestOrException(Long requestId) {
        return requestRepo.findById(requestId).orElseThrow(() ->
                new NotFoundException("Request " + requestId + " not found"));
    }

}
