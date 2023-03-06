package ru.practicum.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.dto.EventDtoOut;
import ru.practicum.dto.UserShortDto;
import ru.practicum.enums.State;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mappers.EventMapper;
import ru.practicum.mappers.UserMapper;
import ru.practicum.model.Event;
import ru.practicum.model.User;
import ru.practicum.repo.EventRepository;
import ru.practicum.repo.UserRepository;
import ru.practicum.service.interfaces.LikesService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.enums.State.PUBLISHED;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class LikesServiceImpl implements LikesService {

    final UserRepository userRepo;

    final EventRepository eventRepo;

    @Override
    public EventDtoOut addLike(Long userId, Long eventId) {
        User user = userOrException(userId);
        Event event = eventOrException(eventId);
        if (event.getLikes().contains(user)) {
            throw new ConflictException("User already liked");
        }
        if (event.getState().equals(PUBLISHED)) {
            user.getLikedEvents().add(event);
            user.getDislikedEvents().remove(event);
            event.getDislikes().remove(user);
            event.getLikes().add(user);
            user.setRating(user.getRating() + 1);
            userRepo.save(user);
            return EventMapper.makeEventDtoOut(event);
        } else {
            throw new ConflictException("Event not posted yet");
        }
    }

    @Override
    public List<EventDtoOut> getUserLikes(Long userId) {
        User user = userOrException(userId);
        return user.getLikedEvents().stream().map(EventMapper::makeEventDtoOut).collect(Collectors.toList());
    }

    @Override
    public void deleteLike(Long userId, Long eventId) {
        User user = userOrException(userId);
        Event event = eventOrException(eventId);
        user.getLikedEvents().remove(event);
        user.setRating(user.getRating() - 1);
        userRepo.save(user);
    }

    @Override
    public EventDtoOut addDislike(Long userId, Long eventId) {
        User user = userOrException(userId);
        Event event = eventOrException(eventId);
        if (event.getDislikes().contains(user)) {
            throw new ConflictException("User already add dislike to event");
        }
        if (event.getState().equals(PUBLISHED)) {
            user.getLikedEvents().remove(event);
            user.getDislikedEvents().add(event);
            event.getDislikes().add(user);
            event.getLikes().remove(user);
            user.setRating(user.getRating() - 1);
            userRepo.save(user);
            return EventMapper.makeEventDtoOut(event);
        } else {
            throw new ConflictException("Event not posted yet");
        }
    }

    @Override
    public void deleteDislike(Long userId, Long eventId) {
        User user = userOrException(userId);
        Event event = eventOrException(eventId);
        user.getDislikedEvents().remove(event);
        user.setRating(user.getRating() + 1);
        userRepo.save(user);

    }

    @Override
    public List<EventDtoOut> getUserDislikes(Long userId) {
        User user = userOrException(userId);
        return user.getDislikedEvents().stream().map(EventMapper::makeEventDtoOut).collect(Collectors.toList());
    }

    @Override
    public List<UserShortDto> getPopularUsers(Pageable pageable) {
        return userRepo.findAllByOrderByRatingDesc(pageable).stream()
                .map(UserMapper::makeUserShortDto).collect(Collectors.toList());
    }

    @Override
    public List<EventDtoOut> getPopularEvents(Pageable pageable) {
        return eventRepo.findAllByState(State.PUBLISHED, pageable).stream()
                .map(EventMapper::makeEventDtoOut).sorted(Comparator.comparingLong(EventDtoOut::getRating).reversed())
                .collect(Collectors.toList());
    }

    private User userOrException(Long id) {
        return userRepo.findById(id).orElseThrow(() ->
                new NotFoundException("User with id: " + id + " not found"));
    }

    private Event eventOrException(Long eventId) {
        return eventRepo.findById(eventId).orElseThrow(() ->
                new NotFoundException("Event with id: " + eventId + " not found"));
    }
}
