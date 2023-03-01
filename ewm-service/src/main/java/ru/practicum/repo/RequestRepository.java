package ru.practicum.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.model.Request;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {

    List<Request> findAllByRequesterIdAndEventId(Long requesterId, Long eventId);

    Long countAllByEvent(Long eventId);

    Long countAllByRequesterIdAndId(Long requesterId, Long Id);

    List<Request> findAllByRequester(Long userId);

}
