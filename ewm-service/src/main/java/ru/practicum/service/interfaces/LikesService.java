package ru.practicum.service.interfaces;

import org.springframework.data.domain.Pageable;
import ru.practicum.dto.EventDtoOut;
import ru.practicum.dto.UserShortDto;

import java.util.List;

public interface LikesService {

    EventDtoOut addLike(Long userId, Long eventId);

    List<EventDtoOut> getUserLikes(Long userId);

    void deleteLike(Long userId, Long eventId);

    EventDtoOut addDislike(Long userId, Long eventId);

    void deleteDislike(Long userId, Long eventId);

    List<EventDtoOut> getUserDislikes(Long userId);

    List<UserShortDto> getPopularUsers(Pageable pageable);

    List<EventDtoOut> getPopularEvents(Pageable pageable);



}
