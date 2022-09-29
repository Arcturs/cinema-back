package ru.vsu.csf.asashina.cinemaback.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.csf.asashina.cinemaback.models.entities.SeatPlanEntity;

@Repository
public interface SeatPlanRepository extends JpaRepository<SeatPlanEntity, Long> {
}
