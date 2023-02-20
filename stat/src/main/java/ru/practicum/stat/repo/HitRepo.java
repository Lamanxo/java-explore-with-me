package ru.practicum.stat.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.stat.model.Hit;

public interface HitRepo extends JpaRepository<Hit, Long> {
}
