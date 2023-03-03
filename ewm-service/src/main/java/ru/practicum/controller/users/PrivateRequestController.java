package ru.practicum.controller.users;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.PartyRequestDto;
import ru.practicum.service.interfaces.RequestService;

import java.util.Collection;

@RestController
@RequestMapping("users/{userId}/requests")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PrivateRequestController {
    final RequestService service;

    @GetMapping
    public Collection<PartyRequestDto> getAllUserRequests(@PathVariable Long userId) {
        return service.getAllUserRequests(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PartyRequestDto addRequest(@PathVariable Long userId,
                                      @RequestParam Long eventId) {
        return service.addRequest(userId, eventId);
    }

    @PatchMapping("/{reqId}/cancel")
    public PartyRequestDto cancelRequest(@PathVariable Long userId,
                                         @PathVariable Long reqId) {
        return service.cancelRequest(userId, reqId);
    }
}
