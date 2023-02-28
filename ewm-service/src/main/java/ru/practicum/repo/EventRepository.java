package ru.practicum.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.enums.State;
import ru.practicum.model.Event;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {

    Optional<Event> findByIdAndInitiatorId(Long eventId, Long initiatorId);

    List<Event> findAllByInitiatorId(Long id, Pageable pageable);

    List<Event> findAll(Specification<Event> specification, Pageable pageable);

    Page<Event> findAllByInitiator_IdInAndState_InAndCategory_IdInAndEventDateBetween
            (Collection<Long> initiatorId, Collection<State> state, Collection<Long> categoryId,
             LocalDateTime rangeStart, LocalDateTime rangeEnd, Pageable pageable);



}
