package ru.vsu.csf.asashina.cinemaback.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.vsu.csf.asashina.cinemaback.models.entities.ScreenEntity;
import ru.vsu.csf.asashina.cinemaback.models.entities.SessionEntity;

import java.time.Instant;
import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<SessionEntity, Long> {

    @Query(value = """
            SELECT *
            FROM session
            WHERE start_time >= :dateNow 
              AND end_time <= :dateFresh""", nativeQuery = true)
    Page<SessionEntity> getFreshSessions(@Param("dateNow") Instant dateNow,
                                         @Param("dateFresh") Instant dateFresh,
                                         Pageable pageable);

    @Query(value = """
            SELECT *
            FROM session
            WHERE start_time >= :dateNow 
              AND end_time <= :dateFresh
              AND movie_id = :movieId""", nativeQuery = true)
    Page<SessionEntity> getFreshSessionsByMovieId(@Param("dateNow") Instant dateNow,
                                         @Param("dateFresh") Instant dateFresh,
                                         @Param("movieId") Long movieId,
                                         Pageable pageable);

    List<SessionEntity> findByScreenAndAndStartTimeAfterAndAndEndTimeBeforeOrOrderByStartTime(ScreenEntity screen,
                                                                            Instant startTime,
                                                                            Instant endTime);
}
