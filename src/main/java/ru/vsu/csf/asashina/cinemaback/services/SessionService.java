package ru.vsu.csf.asashina.cinemaback.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import ru.vsu.csf.asashina.cinemaback.mappers.SessionMapper;
import ru.vsu.csf.asashina.cinemaback.models.SessionDTO;
import ru.vsu.csf.asashina.cinemaback.models.entities.SessionEntity;
import ru.vsu.csf.asashina.cinemaback.repositories.SessionRepository;

import java.time.Instant;

@Service
@AllArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;

    private final SessionMapper sessionMapper;

    @Value("${session.maxFreshDays}")
    private Integer maxDaysToShow;

    public Page<SessionDTO> getAllFreshSessions(Integer pageNumber, Integer size) {
        Instant dateNow = Instant.now();
        Instant dateFuture = dateNow.plusSeconds(fromDaysToSeconds(maxDaysToShow));
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by("startTime").and(Sort.by("endTime")));
        Page<SessionEntity> pages = sessionRepository.getFreshSessions(dateNow, dateFuture, pageable);
        return pages.map(sessionMapper::toDTOFromEntity);
    }

    public Page<SessionDTO> getAllFreshSessionsByMovieId(Long movieId, Integer pageNumber, Integer size) {
        Instant dateNow = Instant.now();
        Instant dateFuture = dateNow.plusSeconds(fromDaysToSeconds(maxDaysToShow));
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by("startTime").and(Sort.by("endTime")));
        Page<SessionEntity> pages = sessionRepository.getFreshSessionsByMovieId(dateNow, dateFuture, movieId, pageable);
        return pages.map(sessionMapper::toDTOFromEntity);
    }

    private int fromDaysToSeconds(Integer days) {
        return days * 24 * 60 * 60;
    }
}
