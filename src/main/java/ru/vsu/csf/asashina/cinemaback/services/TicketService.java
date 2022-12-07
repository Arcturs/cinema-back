package ru.vsu.csf.asashina.cinemaback.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.csf.asashina.cinemaback.mappers.TicketMapper;
import ru.vsu.csf.asashina.cinemaback.models.dtos.SeatPlanDTO;
import ru.vsu.csf.asashina.cinemaback.models.dtos.UserDTO;
import ru.vsu.csf.asashina.cinemaback.models.dtos.session.SessionPageDTO;
import ru.vsu.csf.asashina.cinemaback.models.entities.SessionEntity;
import ru.vsu.csf.asashina.cinemaback.models.entities.TicketEntity;
import ru.vsu.csf.asashina.cinemaback.models.entities.UserEntity;
import ru.vsu.csf.asashina.cinemaback.repositories.TicketRepository;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {

    private static final String ORDER_ID_FORMAT = "%s%s%s-%08d";

    @Value("${order.paymentMin}")
    private Integer paymentMin;

    private final TicketRepository ticketRepository;

    private final TicketMapper ticketMapper;

    private final Clock clock;

    private final SessionService sessionService;
    private final UserService userService;
    private final SeatService seatService;

    @Transactional
    public String createNewOrder(List<SeatPlanDTO> seatPlan, SessionPageDTO session, UserDTO user) {
        Instant startTransactionTime = Instant.now(clock);
        String orderId = generateOrderId(startTransactionTime);
        ticketRepository.saveAll(createTickets(seatPlan, sessionService.fromDTOToEntity(session),
                userService.fromDTOToEntity(user), orderId, startTransactionTime));
        return orderId;
    }

    public String generateOrderId(Instant startTransactionTime) {
        int increment = 1;
        Integer code = ticketRepository.findOrderNumberForUpdate() + increment;
        ticketRepository.updateOrderNumber(increment);
        LocalDateTime start = LocalDateTime.ofInstant(startTransactionTime,
                clock.getZone().getRules().getOffset(Instant.now(clock)));
        return String.format(ORDER_ID_FORMAT, start.getYear(), start.getMonthValue(), start.getDayOfMonth(), code);
    }

    private List<TicketEntity> createTickets(List<SeatPlanDTO> seatPlan, SessionEntity session, UserEntity user,
                                             String orderId, Instant startTransactionTime) {
        Instant endTransactionTime = startTransactionTime.plusSeconds(fromMinutesToSeconds(paymentMin));
        return seatPlan.stream()
                .map(el -> ticketMapper.toEntity(user, session, seatService.fromDTOToEntity(el.getSeat()), orderId,
                        false, startTransactionTime, endTransactionTime))
                .toList();
    }

    private Integer fromMinutesToSeconds(int minutes) {
        return minutes * 60;
    }
}