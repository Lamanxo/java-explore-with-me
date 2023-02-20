package ru.practicum.stat.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.stat.dto.StatDtoOut;
import ru.practicum.stat.model.Hit;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HitRepo extends JpaRepository<Hit, Long> {

    @Query("SELECT new ru.practicum.stat.dto.StatDtoOut(h.app, h.uri, COUNT(h.ip)) " +
            "FROM Hit h " +
            "WHERE h.timestamp BETWEEN ?1 AND ?2 " +
            "AND h.uri IN (?3) " +
            "GROUP BY h.app, h.uri " +
            "ORDER BY COUNT(h.ip) DESC")
    List<StatDtoOut> getNonUniqueStat(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("SELECT new ru.practicum.stat.dto.StatDtoOut(h.app, h.uri, COUNT(DISTINCT h.ip)) " +
            "FROM Hit h " +
            "WHERE h.timestamp BETWEEN ?1 AND ?2 " +
            "AND h.uri IN (?3) " +
            "GROUP BY h.app, h.uri " +
            "ORDER BY COUNT(DISTINCT h.ip) DESC")
    List<StatDtoOut> getUniqueStat(LocalDateTime start, LocalDateTime end, List<String> uris);

}
