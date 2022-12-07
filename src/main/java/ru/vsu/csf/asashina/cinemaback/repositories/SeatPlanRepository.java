package ru.vsu.csf.asashina.cinemaback.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.vsu.csf.asashina.cinemaback.models.entities.SeatPlanEntity;

import java.util.List;

@Repository
public interface SeatPlanRepository extends JpaRepository<SeatPlanEntity, Long> {

    @Query(value = "SELECT * FROM seat_plan_for_session WHERE seat_plan_for_session_id IN (:seatPlanIds) FOR UPDATE",
            nativeQuery = true)
    List<SeatPlanEntity> findIdsForUpdate(@Param("seatPlanIds") List<Long> seatPlanIds);

    @Modifying
    @Query(value = """
                UPDATE seat_plan_for_session
                SET is_available = true
                WHERE session_id = :sessionId AND seat_id IN (:seatsIds)""", nativeQuery = true)
    void freeSeatsBySessionId(@Param("seatsIds") List<Long> seatsIds,
                              @Param("sessionId") Long sessionId);
}
