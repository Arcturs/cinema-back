package ru.vsu.csf.asashina.cinemaback.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.csf.asashina.cinemaback.models.entities.ScreenEntity;
import ru.vsu.csf.asashina.cinemaback.models.entities.SeatEntity;
import ru.vsu.csf.asashina.cinemaback.repositories.SeatRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class SeatService {

    private final SeatRepository seatRepository;

    @Transactional
    public void createSeats(ScreenEntity screen) {
        List<SeatEntity> seats = new ArrayList<>();
        for (int i = 1; i <= screen.getRows(); i++) {
            for (int j = 1; j <= screen.getSeats(); j++) {
                seats.add(new SeatEntity(null, i, j, screen));
            }
        }
        seatRepository.saveAll(seats);
    }
}
