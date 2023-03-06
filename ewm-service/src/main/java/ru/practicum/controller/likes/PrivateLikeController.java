package ru.practicum.controller.likes;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.EventDtoOut;
import ru.practicum.service.interfaces.LikesService;

import java.util.Collection;

@RestController
@RequestMapping("/users/{userId}")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PrivateLikeController {
    final LikesService service;

    @PatchMapping("/like/{eventId}")
    public EventDtoOut addLike(@PathVariable Long userId,
                               @PathVariable Long eventId) {
        log.info("user with id {} to like event {}", userId, eventId);
        return service.addLike(userId, eventId);
    }

    @DeleteMapping("/like/{eventId}")
    public void deleteLike(@PathVariable Long userId,
                           @PathVariable Long eventId) {
        log.info("user {} delete like event {}", userId, eventId);
        service.deleteLike(userId, eventId);
    }

    @GetMapping("/like")
    public Collection<EventDtoOut> getUserLikes(@PathVariable Long userId) {
        log.info("user with id {} get liked events", userId);
        return service.getUserLikes(userId);
    }

    @GetMapping("/dislike")
    public Collection<EventDtoOut> getUserDislikes(@PathVariable Long userId) {
        log.info("user with id {} get disliked event", userId);
        return service.getUserDislikes(userId);
    }

    @PatchMapping("/dislike/{eventId}")
    public EventDtoOut addDislike(@PathVariable Long userId,
                                    @PathVariable Long eventId) {
        log.info("user {} dislike event {}", userId, eventId);
        return service.addDislike(userId, eventId);
    }

    @DeleteMapping("/dislike/{eventId}")
    public void deleteDisLike(@PathVariable Long userId,
                              @PathVariable Long eventId) {
        log.info("user {} delete dislike event {}", userId, eventId);
        service.deleteDislike(userId, eventId);
    }
}
