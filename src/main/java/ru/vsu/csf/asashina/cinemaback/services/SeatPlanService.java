package ru.vsu.csf.asashina.cinemaback.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.csf.asashina.cinemaback.exceptions.ChosenSeatsAreEmptyException;
import ru.vsu.csf.asashina.cinemaback.exceptions.SeatsAreAlreadyBookedException;
import ru.vsu.csf.asashina.cinemaback.mappers.SeatPlanMapper;
import ru.vsu.csf.asashina.cinemaback.models.dtos.SeatPlanDTO;
import ru.vsu.csf.asashina.cinemaback.models.entities.ScreenEntity;
import ru.vsu.csf.asashina.cinemaback.models.entities.SeatPlanEntity;
import ru.vsu.csf.asashina.cinemaback.models.entities.SessionEntity;
import ru.vsu.csf.asashina.cinemaback.repositories.SeatPlanRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class SeatPlanService {

    private final SeatPlanRepository seatPlanRepository;

    private final SeatPlanMapper seatPlanMapper;

    @Transactional
    public void createSeatPlan(ScreenEntity screen, SessionEntity session) {
        List<SeatPlanEntity> seatPlan = screen.getSeatsSet().stream()
                .map(el -> new SeatPlanEntity(session, el))
                .toList();
        seatPlanRepository.saveAll(seatPlan);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public List<SeatPlanDTO> bookSeatsForProceeding(List<Long> seatPlanIds, Long sessionId) {
        if (seatPlanIds.isEmpty()) {
            throw new ChosenSeatsAreEmptyException("You have not chosen any seats");
        }
        List<SeatPlanEntity> bookedSeats = seatPlanRepository.findIdsForUpdate(seatPlanIds);
        bookedSeats = validateSeatPlanForAlreadyBookedSeatsAndCorrectSessionId(bookedSeats, sessionId);
        if (bookedSeats.isEmpty()) {
            throw new SeatsAreAlreadyBookedException("Following seats are already booked");
        }
        return seatPlanMapper.fromEntityToDTOList(seatPlanRepository.saveAll(bookedSeats));
    }

    private List<SeatPlanEntity> validateSeatPlanForAlreadyBookedSeatsAndCorrectSessionId(
            List<SeatPlanEntity> bookedSeats, Long sessionId) {
        return bookedSeats.stream()
                .filter(el -> el.getIsAvailable() && el.getSession().getSessionId().equals(sessionId))
                .peek(el -> el.setIsAvailable(false))
                .toList();
    }
}
