package ru.vsu.csf.asashina.cinemaback.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
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
}
