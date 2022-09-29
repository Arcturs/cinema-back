package ru.vsu.csf.asashina.cinemaback.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.csf.asashina.cinemaback.models.entities.ScreenEntity;
import ru.vsu.csf.asashina.cinemaback.models.entities.SeatPlanEntity;
import ru.vsu.csf.asashina.cinemaback.models.entities.SessionEntity;
import ru.vsu.csf.asashina.cinemaback.repositories.SeatPlanRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class SeatPlanService {

    private final SeatPlanRepository seatPlanRepository;

    @Transactional
    public void createSeatPlan(ScreenEntity screen, SessionEntity session) {
        List<SeatPlanEntity> seatPlan = screen.getSeatsSet().stream()
                .map(el -> new SeatPlanEntity(session, el))
                .toList();
        seatPlanRepository.saveAll(seatPlan);
    }
}
