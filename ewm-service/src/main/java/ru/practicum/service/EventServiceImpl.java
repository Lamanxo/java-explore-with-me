package ru.practicum.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.dto.*;
import ru.practicum.enums.State;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.model.Event;
import ru.practicum.model.User;
import ru.practicum.repo.CategoryRepository;
import ru.practicum.repo.EventRepository;
import ru.practicum.repo.UserRepository;
import ru.practicum.service.interfaces.CategoryService;
import ru.practicum.service.interfaces.EventService;
import ru.practicum.service.interfaces.UserService;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.mappers.EventMapper.*;
import static ru.practicum.mappers.CategoryMapper.*;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventServiceImpl implements EventService {

    final EventRepository eventRepo;

    final UserService userService;

    final UserRepository userRepo;

    final CategoryService catService;

    final CategoryRepository catRepo;

    @Override
    public EventDto addByUser(Long userId, EventDtoIn eventDtoIn) {
        if (eventDtoIn.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            throw new ConflictException("Wrong Event date");
        }
        Event event = makeEvent(eventDtoIn);
        event.setInitiator(userOrException(userId));
        event.setCategory(catRepo.findById(eventDtoIn.getCategory()).orElseThrow(() ->
                new NotFoundException("Category with id: " + eventDtoIn.getCategory() + " not found")));

        return makeEventDto(eventRepo.save(event));
    }

    @Override
    public List<EventDtoOut> getAllByUser(Long userId, Pageable pageable) {

        return null;
    }

    @Override
    public EventDto updateEventByUser(Long userId, Long eventId, EventDtoUserUpdated dtoUserUpdated) {

        return null;
    }

    @Override
    public EventDto getByUser(Long userId, Long eventId) {
        return makeEventDto(eventRepo.findByIdAndInitiatorId(eventId,userId).orElseThrow(() ->
                new NotFoundException("Event: " + eventId + " or User with id: " + userId + " not found")));
    }

    @Override
    public EventDto getByIdPublic(Long id) {
        return makeEventDto(eventRepo.findById(id).orElseThrow(() ->
                new NotFoundException("Event with id: " + id + " not found")));
    }

    @Override
    public List<EventDtoOut> getAllByPublic(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean onlyAvailable, String sort, Pageable pageable) {
        return null;
    }

    @Override
    public EventDto updateEventByAdmin(Long eventId, EventDtoAdminUpdated dtoAdminUpdated) {
        return null;
    }

    @Override
    public List<EventDto> getAllByAdmin(List<Long> users, List<State> states, List<Long> categories, LocalDateTime rangeStart, LocalDateTime rangeEnd, Pageable pageable) {
        return null;
    }

    private User userOrException(Long id) {
        return userRepo.findById(id).orElseThrow(() ->
                new NotFoundException("User with id: " + id + " not found"));
    }

    private EventDtoOut makeEventDtoOutFinal(Event event) {
        EventDtoOut dtoOut = makeEventDtoOut(event);
        dtoOut.setCategory(catService.getCategoryById(event.getCategory().getId()));
        return dtoOut;
    }
}
