package ru.practicum.repo;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByIdIsIn(List<Long> ids, Pageable pageable);

    @Query("select u from User u order by u.rating DESC")
    List<User> findAllByOrderByRatingDesc(Pageable pageable);

}
