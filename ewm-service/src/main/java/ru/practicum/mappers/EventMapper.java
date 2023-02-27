package ru.practicum.mappers;

import ru.practicum.dto.EventDto;
import ru.practicum.model.Event;

import static ru.practicum.mappers.CategoryMapper.*;
import static ru.practicum.mappers.UserMapper.*;


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
}
