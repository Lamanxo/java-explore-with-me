package ru.practicum.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.enums.Status;
import ru.practicum.model.Request;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {

    @Query("select r from Request r where r.requester.id = ?1 and r.event.id = ?2")
    List<Request> findAllByRequesterIdAndEventId(Long requesterId, Long eventId);

    @Query("select count(r) from Request r where r.event.id = ?1")
    Long countAllByEventId(Long eventId);

    Long countAllByRequesterIdAndId(Long requesterId, Long id);

    @Query("select r from Request r where r.requester.id = ?1")
    List<Request> findAllByRequesterId(Long userId);

    @Query("select r from Request r join Event e on e.id=r.event.id where e.initiator.id = ?1 and r.event.id = ?2")
    List<Request> getAllRequestsOfUser(Long userId, Long eventId);

    @Query("SELECT COUNT(r.event) FROM Request r " +
            "WHERE r.event.id = ?1 AND r.status = ?2")
    Long getRequestsWithConfirmedStatus(Long eventId, Status confirmed);


    @Query("select r from Request r where r.id = ?2 and r.event.id = ?1")
    List<Request> findAllByIdAndEventId(Long eventId, List<Long> ids);
}
