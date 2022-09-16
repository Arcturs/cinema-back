package ru.vsu.csf.asashina.cinemaback.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.csf.asashina.cinemaback.models.entities.SeatEntity;

@Repository
public interface SeatRepository extends JpaRepository<SeatEntity, Long> {
}
