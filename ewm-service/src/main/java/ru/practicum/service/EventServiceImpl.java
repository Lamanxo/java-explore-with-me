package ru.practicum.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.*;
import ru.practicum.enums.AdminStateAction;
import ru.practicum.enums.State;
import ru.practicum.enums.Status;
import ru.practicum.enums.UserStateAction;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mappers.RequestMapper;
import ru.practicum.model.Category;
import ru.practicum.model.Event;
import ru.practicum.model.Request;
import ru.practicum.model.User;
import ru.practicum.repo.CategoryRepository;
import ru.practicum.repo.EventRepository;
import ru.practicum.repo.RequestRepository;
import ru.practicum.repo.UserRepository;
import ru.practicum.service.interfaces.CategoryService;
import ru.practicum.service.interfaces.EventService;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static ru.practicum.enums.Status.CONFIRMED;
import static ru.practicum.enums.Status.REJECTED;
import static ru.practicum.mappers.EventMapper.*;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventServiceImpl implements EventService {

    final EventRepository eventRepo;
    final UserRepository userRepo;
    final CategoryService catService;
    final CategoryRepository catRepo;
    final RequestRepository requestRepo;

    @Override
    @Transactional
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
        userOrException(userId);
        List<EventDtoOut> dto = new ArrayList<>();
        for (Event event : eventRepo.findAllByInitiatorId(userId,pageable)) {
            dto.add(makeEventDtoOutFinal(event));
        }
        return dto;
    }

    @Override
    @Transactional
    public EventDto updateEventByUser(Long userId, Long eventId, EventDtoUserUpdated dtoUserUpdated) {
        Event event = eventOrException(eventId);
        if (event.getState().equals(State.PUBLISHED)) {
            throw new ConflictException("Event already posted");
        }
        if (dtoUserUpdated.getCategory() != null) {
            Category category = catRepo.findById(dtoUserUpdated.getCategory()).orElseThrow(() ->
                    new NotFoundException("Category with id: " + dtoUserUpdated.getCategory() + " not found"));
            event.setCategory(category);
        }
        if (dtoUserUpdated.getEventDate() != null) {
            if (dtoUserUpdated.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
                throw new ConflictException("Invalid event date acquired");
            }
        }
        if (dtoUserUpdated.getStateAction() == UserStateAction.SEND_TO_REVIEW) {
            event.setState(State.PENDING);
        }
        if (dtoUserUpdated.getStateAction() == UserStateAction.CANCEL_REVIEW) {
            event.setState(State.CANCELED);
        }
        if (dtoUserUpdated.getAnnotation() != null) {
            event.setAnnotation(dtoUserUpdated.getAnnotation());
        }
        if (dtoUserUpdated.getDescription() != null) {
            event.setDescription(dtoUserUpdated.getDescription());
        }
        if (dtoUserUpdated.getTitle() != null) {
            event.setTitle(dtoUserUpdated.getTitle());
        }
        if (dtoUserUpdated.getRequestModeration() != null) {
            event.setRequestModeration(dtoUserUpdated.getRequestModeration());
        }
        if (dtoUserUpdated.getParticipantLimit() != null) {
            event.setParticipantLimit(dtoUserUpdated.getParticipantLimit());
        }
        if (dtoUserUpdated.getPaid() != null) {
            event.setPaid(dtoUserUpdated.getPaid());
        }
        if (dtoUserUpdated.getLocation() != null) {
            event.setLocation(dtoUserUpdated.getLocation());
        }
        return makeEventDto(eventRepo.save(event));
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
    public List<EventDtoOut> getAllByPublic(String text, List<Long> categories,
                                            Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                            Boolean onlyAvailable, Pageable pageable) {
        List<EventDtoOut> dtoList = new ArrayList<>();
        LocalDateTime startDate = Objects.requireNonNullElseGet(rangeStart, () -> LocalDateTime.now().plusYears(30));
        LocalDateTime endDate = Objects.requireNonNullElseGet(rangeEnd, () -> LocalDateTime.now().minusYears(30));
        Specification<Event> specification = (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Subquery<Long> subQuery = query.subquery(Long.class);
            Root<Request> requestRoot = subQuery.from(Request.class);
            Join<Request, Event> eventsRequests = requestRoot.join("event");

            predicates.add(builder.equal(root.get("state"), State.PUBLISHED));
            if (text != null && !text.isEmpty()) {
                predicates.add(builder.or(builder.like(builder.lower(root.get("annotation")), "%" + text.toLowerCase() + "%"),
                        builder.like(builder.lower(root.get("description")), "%" + text.toLowerCase() + "%")));
            }
            if (categories != null) {
                predicates.add(builder.and(root.get("category").in(categories)));
            }
            if (paid != null) {
                predicates.add(builder.equal(root.get("paid"), paid));
            }
            predicates.add(builder.greaterThan(root.get("eventDate"), startDate));
            predicates.add(builder.lessThan(root.get("eventDate"), endDate));
            if (onlyAvailable != null && onlyAvailable) {
                predicates.add(builder.or(builder.equal(root.get("participantLimit"), 0),
                        builder.and(builder.notEqual(root.get("participantLimit"), 0),
                                builder.greaterThan(root.get("participantLimit"), subQuery.select(builder.count(requestRoot.get("id")))
                                        .where(builder.equal(eventsRequests.get("id"), requestRoot.get("event").get("id")))
                                        .where(builder.equal(requestRoot.get("status"), CONFIRMED))))));

            }
            return builder.and(predicates.toArray(new Predicate[0]));


        };
        for (Event event : eventRepo.findAll(specification, pageable)) {
            dtoList.add(makeEventDtoOutFinal(event));
        }
        return dtoList;
    }

    @Override
    @Transactional
    public EventDto updateEventByAdmin(Long eventId, EventDtoAdminUpdated dtoAdminUpdated) {
        Event event = eventOrException(eventId);
        if (dtoAdminUpdated.getStateAction() == AdminStateAction.PUBLISH_EVENT) {
            if (event.getState() != State.PENDING) {
                throw new ConflictException("Event with id: " + eventId + " cant posted");
            }
            LocalDateTime published = LocalDateTime.now();
            event.setPublishedOn(published);
            event.setState(State.PUBLISHED);
        }

        if (dtoAdminUpdated.getStateAction() == AdminStateAction.REJECT_EVENT) {
            if (event.getState() == State.PUBLISHED && event.getPublishedOn().isBefore(LocalDateTime.now())) {
                throw new ConflictException("Event with id: " + eventId + " cant posted");
            }
            event.setState(State.CANCELED);
        }
        if (dtoAdminUpdated.getAnnotation() != null) {
            event.setAnnotation(dtoAdminUpdated.getAnnotation());
        }

        if (dtoAdminUpdated.getCategory() != null) {
            event.setCategory(catRepo.findById(dtoAdminUpdated.getCategory()).orElseThrow(() ->
                    new NotFoundException("Category with id: " + dtoAdminUpdated.getCategory() + " not found")));
        }

        if (dtoAdminUpdated.getDescription() != null) {
            event.setDescription(dtoAdminUpdated.getDescription());
        }

        if (dtoAdminUpdated.getEventDate() != null) {
            if (dtoAdminUpdated.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
                throw new ConflictException("Event date is incorrect");
            }
            event.setEventDate(dtoAdminUpdated.getEventDate());
        }
        if (dtoAdminUpdated.getPaid() != null) {
            event.setPaid(dtoAdminUpdated.getPaid());
        }
        if (dtoAdminUpdated.getParticipantLimit() != null) {
            event.setParticipantLimit(dtoAdminUpdated.getParticipantLimit());
        }
        if (dtoAdminUpdated.getRequestModeration() != null) {
            event.setRequestModeration(dtoAdminUpdated.getRequestModeration());
        }
        if (dtoAdminUpdated.getTitle() != null) {
            event.setTitle(dtoAdminUpdated.getTitle());
        }
        return makeEventDto(eventRepo.save(event));
    }

    @Override
    public List<EventDto> getAllByAdmin(List<Long> users, List<State> states, List<Long> categories,
                                        LocalDateTime rangeStart, LocalDateTime rangeEnd, Pageable pageable) {
        List<EventDto> dtoList = new ArrayList<>();
        for (Event event : eventRepo.findAllByInitiator_IdInAndState_InAndCategory_IdInAndEventDateBetween(users,
                states, categories, rangeStart, rangeEnd,pageable)) {
            dtoList.add(makeEventDto(event));
        }
        return dtoList;
    }

    @Override
    public Collection<PartyRequestDto> getRequestsOfEvent(Long userId, Long eventId) {
        userOrException(userId);
        Event event = eventOrException(eventId);
        if (!event.getInitiator().getId().equals(userId)) {
            throw new ConflictException("User " + userId + " are not initiator of event");
        }
        List<PartyRequestDto> dtoList = new ArrayList<>();
        for (var request : requestRepo.findAllByEventId(eventId)) {
            dtoList.add(RequestMapper.makerRequestDto(request));
        }
        return dtoList;
    }

    @Override
    @Transactional
    public RequestStatusDtoOut updateRequest(Long userId, Long eventId, RequestStatusDtoIn dtoIn) {
        userOrException(userId);
        Event event = eventOrException(eventId);
        if (!event.getInitiator().getId().equals(userId)) {
            throw new ConflictException("User " + userId + " are not initiator of event");
        }
        List<Request> requests = requestRepo.findAllByRequesterIdAndEventId(eventId, dtoIn.getRequestIds());
        return requestsFinalUpdate(requests, Status.valueOf(dtoIn.getStatus()), event);

    }

    private RequestStatusDtoOut requestsFinalUpdate(List<Request> requests, Status status, Event event) {
        if (status.equals(CONFIRMED)) {
            return confirmedStatus(requests, event);
        } else if (status.equals(REJECTED)) {
            return rejectStatus(requests, event);
        } else {
            return new RequestStatusDtoOut();
        }
    }

    private RequestStatusDtoOut confirmedStatus(List<Request> requests, Event event) {
        RequestStatusDtoOut dtoOut = new RequestStatusDtoOut(new ArrayList<>(), new ArrayList<>());
        for (var request : requests) {
            try {
                statusPendingOrException(request);
            } catch (ConflictException e) {
                continue;
            }
            if (event.getParticipantLimit() - requestRepo.getRequestsWithConfirmedStatus(event.getId(), CONFIRMED) <= 0) {
                throw new ConflictException("Event already full");
            } else {
                request.setStatus(CONFIRMED);
                request = requestRepo.save(request);
                dtoOut.getConfirmedRequests().add(RequestMapper.makerRequestDto(request));
            }
        }
        return dtoOut;
    }

    private RequestStatusDtoOut rejectStatus(List<Request> requests, Event event) {
        RequestStatusDtoOut dtoOut = new RequestStatusDtoOut(new ArrayList<>(), new ArrayList<>());
        for (Request request : requests) {
            statusPendingOrException(request);
            request.setStatus(Status.REJECTED);
            request = requestRepo.save(request);
            dtoOut.getRejectedRequests().add(RequestMapper.makerRequestDto(request));
        }
        return dtoOut;
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

    private Event eventOrException(Long eventId) {
        return eventRepo.findById(eventId).orElseThrow(() ->
                new NotFoundException("Event with id: " + eventId + " not found"));
    }

    private void statusPendingOrException(Request request) {
        if (!request.getStatus().equals(Status.PENDING)) {
            throw new ConflictException("Request status must be PENDING");
        }
    }
}
