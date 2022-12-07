package ru.vsu.csf.asashina.cinemaback.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.csf.asashina.cinemaback.exceptions.ObjectNotExistsException;
import ru.vsu.csf.asashina.cinemaback.exceptions.SessionDateTimeException;
import ru.vsu.csf.asashina.cinemaback.exceptions.SessionHasAlreadyStartedException;
import ru.vsu.csf.asashina.cinemaback.mappers.SessionMapper;
import ru.vsu.csf.asashina.cinemaback.models.dtos.session.SessionDTO;
import ru.vsu.csf.asashina.cinemaback.models.dtos.session.SessionPageDTO;
import ru.vsu.csf.asashina.cinemaback.models.entities.MovieEntity;
import ru.vsu.csf.asashina.cinemaback.models.entities.ScreenEntity;
import ru.vsu.csf.asashina.cinemaback.models.entities.SessionEntity;
import ru.vsu.csf.asashina.cinemaback.models.request.SessionRequest;
import ru.vsu.csf.asashina.cinemaback.repositories.SessionRepository;

import java.time.*;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final MovieService movieService;
    private final ScreenService screenService;
    private final SeatPlanService seatPlanService;

    private final SessionRepository sessionRepository;

    private final SessionMapper sessionMapper;

    private final Clock clock;

    @Value("${session.maxFreshDays}")
    private Integer maxDaysToShow;

    @Value("${session.cleaningTimeInMinutes}")
    private Integer cleaningTimeInMinutes;

    public Page<SessionPageDTO> getAllFreshSessions(Long movieId, Integer pageNumber, Integer size) {
        Instant dateNow = Instant.now(clock);
        Instant dateFuture = dateNow.plusSeconds(fromDaysToSeconds(maxDaysToShow));
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by("start_time").and(Sort.by("end_time")));
        Page<SessionEntity> pages = sessionRepository.getFreshSessions(dateNow, dateFuture, movieId, pageable);
        return pages.map(sessionMapper::toPageDTOFromEntity);
    }

    public SessionDTO getSessionById(Long sessionId) {
        SessionEntity session = sessionRepository.findById(sessionId).orElseThrow(
                () -> new ObjectNotExistsException("Session with following id does not exist")
        );
        return sessionMapper.toDTOFromEntity(session);
    }

    @Transactional
    public void createSession(SessionRequest request) {
        MovieEntity movie = movieService.findMovieById(request.getMovieId());
        ScreenEntity screen = screenService.getEntityById(request.getScreenId());
        Instant start = LocalDateTime.of(request.getStartDate(), request.getStartTime())
                .toInstant(clock.getZone().getRules().getOffset(Instant.now(clock)));
        validateStartDateAndStartTime(null, start, movie.getDuration(), screen);
        SessionEntity entity = sessionRepository.save(sessionMapper.fromRequestToEntity(request, start,
                getFinishDateTime(start, movie.getDuration()), movie, screen));
        seatPlanService.createSeatPlan(screen, entity);
    }

    @Transactional
    public SessionDTO updateSession(Long sessionId, SessionRequest request) {
        SessionEntity session = sessionRepository.findById(sessionId).orElseThrow(
                () -> new ObjectNotExistsException("Session with following id does not exist")
        );
        MovieEntity movie = movieService.findMovieById(session.getMovie().getMovieId());
        ScreenEntity screen = screenService.getEntityById(request.getScreenId());
        Instant start = LocalDateTime.of(request.getStartDate(), request.getStartTime())
                .toInstant(clock.getZone().getRules().getOffset(Instant.now(clock)));
        validateStartDateAndStartTime(sessionId, start, movie.getDuration(), screen);
        sessionMapper.updateSessionEntity(session, request, start,
                getFinishDateTime(start, movie.getDuration()), movie, screen);
        return sessionMapper.toDTOFromEntity(sessionRepository.save(session));
    }

    @Transactional
    public void deleteSession(Long sessionId) {
        getSessionById(sessionId);
        sessionRepository.deleteById(sessionId);
    }

    public SessionPageDTO validateIsSessionStarted(Long sessionId) {
        SessionEntity session = sessionRepository.findById(sessionId).orElseThrow(
                () -> new ObjectNotExistsException("Session with following id does not exist")
        );
        if (session.getStartTime().isBefore(Instant.now(clock))) {
            throw new SessionHasAlreadyStartedException("Session has already started");
        }
        return sessionMapper.toPageDTOFromEntity(session);
    }

    public SessionEntity fromDTOToEntity(SessionPageDTO sessionPageDTO) {
        return sessionMapper.fromDTOToEntity(sessionPageDTO);
    }

    private int fromDaysToSeconds(Integer days) {
        return days * 24 * 60 * 60;
    }

    private int fromMinutesToSeconds(Integer minutes) {
        return minutes * 60;
    }

    private Instant getFinishDateTime(Instant start, LocalTime duration) {
        int durationInSeconds = (duration.getHour() * 60 + duration.getMinute()) * 60;
        return start.plusSeconds(durationInSeconds);
    }

    private void validateStartDateAndStartTime(Long sessionId, Instant start, LocalTime duration, ScreenEntity screen) {
        Instant now = Instant.now(clock);
        Instant nowPlusOneDay = now.plusSeconds(fromDaysToSeconds(1));
        if (start.isBefore(nowPlusOneDay)) {
            throw new SessionDateTimeException("You cannot create/change session which will starts in 24 hours");
        }

        Instant finish = getFinishDateTime(start, duration);
        List<SessionEntity> sessions =
                sessionRepository.findByScreenIdAndAndStartTimeAfterAndAndEndTimeBeforeOrOrderByStartTime(
                        screen.getScreenId(), start.minusSeconds(fromDaysToSeconds(1)),
                        finish.plusSeconds(fromDaysToSeconds(1)));
        for (SessionEntity session : sessions) {
            if (session.getSessionId().equals(sessionId)) {
                continue;
            }
            if (session.getEndTime().plusSeconds(fromMinutesToSeconds(cleaningTimeInMinutes)).isAfter(start)
                    && session.getEndTime().plusSeconds(fromMinutesToSeconds(cleaningTimeInMinutes)).isBefore(finish)) {
                throw new SessionDateTimeException("Session start time affects other sessions");
            }
            if (session.getStartTime().isBefore(finish.plusSeconds(fromMinutesToSeconds(cleaningTimeInMinutes)))
                    && session.getStartTime().isAfter(start)) {
                throw new SessionDateTimeException("Session end time affects other sessions");
            }
        }

        if (start.isBefore(now) || start.isAfter(now.plusSeconds(fromDaysToSeconds(365)))) {
            throw new SessionDateTimeException("You cannot create sessions in past/future");
        }

        LocalDateTime dateTimeStart = LocalDateTime.ofInstant(start, clock.getZone());
        if (dateTimeStart.getMinute() != 0 && dateTimeStart.getMinute() != 30) {
            throw new SessionDateTimeException("Session's start time must be HH:00 or HH:30");
        }
    }
}
