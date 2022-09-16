package ru.vsu.csf.asashina.cinemaback.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.csf.asashina.cinemaback.models.entities.SessionEntity;

@Repository
public interface SessionRepository extends JpaRepository<SessionEntity, Long> {
}
