package ru.practicum.mappers;

import lombok.experimental.UtilityClass;
import ru.practicum.dto.EventDto;
import ru.practicum.dto.EventDtoIn;
import ru.practicum.dto.EventDtoOut;
import ru.practicum.enums.State;
import ru.practicum.model.Event;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.stream.Collectors;

import static ru.practicum.mappers.CategoryMapper.makeCategoryDto;
import static ru.practicum.mappers.UserMapper.makeUserShortDto;

@UtilityClass
public class EventMapper {

    public static EventDto makeEventDto(Event event) {
        EventDto eventDto = new EventDto();
        eventDto.setId(event.getId());
        eventDto.setAnnotation(event.getAnnotation());
        eventDto.setCategory(makeCategoryDto(event.getCategory()));
        eventDto.setEventDate(event.getEventDate());
        eventDto.setInitiator(makeUserShortDto(event.getInitiator()));
        eventDto.setLocation(event.getLocation());
        eventDto.setPaid(event.getPaid());
        eventDto.setTitle(event.getTitle());
        eventDto.setConfirmedRequests(event.getConfirmedRequests());
        eventDto.setCreatedOn(event.getCreatedOn());
        eventDto.setDescription(event.getDescription());
        eventDto.setParticipantLimit(event.getParticipantLimit());
        eventDto.setPublishedOn(event.getPublishedOn());
        eventDto.setRequestModeration(event.getRequestModeration());
        eventDto.setState(event.getState());
        eventDto.setViews(event.getViews());
        return eventDto;
    }

    public static Event makeEvent(EventDtoIn dtoIn) {
        Event event = new Event();
        event.setAnnotation(dtoIn.getAnnotation());
        event.setEventDate(dtoIn.getEventDate());
        event.setLocation(dtoIn.getLocation());
        event.setPaid(dtoIn.getPaid());
        event.setTitle(dtoIn.getTitle());
        event.setConfirmedRequests(0L);
        event.setCreatedOn(LocalDateTime.now());
        event.setDescription(dtoIn.getDescription());
        event.setParticipantLimit(dtoIn.getParticipantLimit());
        event.setPublishedOn(LocalDateTime.now());
        event.setRequestModeration(dtoIn.getRequestModeration());
        event.setState(State.PENDING);
        event.setViews(0L);
        event.setLikes(new HashSet<>());
        event.setDislikes(new HashSet<>());
        return event;
    }

    public static EventDtoOut makeEventDtoOut(Event event) {
        EventDtoOut dtoOut = new EventDtoOut();
        dtoOut.setId(event.getId());
        dtoOut.setAnnotation(event.getAnnotation());
        dtoOut.setEventDate(event.getEventDate());
        dtoOut.setInitiator(makeUserShortDto(event.getInitiator()));
        dtoOut.setPaid(event.getPaid());
        dtoOut.setCategory(CategoryMapper.makeCategoryDto(event.getCategory()));
        dtoOut.setTitle(event.getTitle());
        dtoOut.setConfirmedRequests(event.getConfirmedRequests());
        dtoOut.setViews(event.getViews());
        dtoOut.setDislikes(event.getLikes().stream().map(UserMapper::makeUserShortDto).collect(Collectors.toSet()));
        dtoOut.setLikes(event.getLikes().stream().map(UserMapper::makeUserShortDto).collect(Collectors.toSet()));
        dtoOut.setRating((long) event.getLikes().size() - (long) event.getDislikes().size());
        return dtoOut;
    }
}
