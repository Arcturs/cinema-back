package ru.vsu.csf.asashina.cinemaback.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.csf.asashina.cinemaback.exceptions.ObjectNotExistsException;
import ru.vsu.csf.asashina.cinemaback.exceptions.SessionHasAlreadyStartedException;
import ru.vsu.csf.asashina.cinemaback.exceptions.TicketDoesNotBelongToUserException;
import ru.vsu.csf.asashina.cinemaback.mappers.TicketMapper;
import ru.vsu.csf.asashina.cinemaback.models.dtos.SeatPlanDTO;
import ru.vsu.csf.asashina.cinemaback.models.dtos.TicketDTO;
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

    @Value("${order.bookingTimeRestrictionBeforeFilmStartsMin}")
    private Integer bookingTimeRestrictionBeforeFilmStartsMin;

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

    public List<TicketDTO> getTicketDetails(String orderId, Authentication authentication) {
        return ticketMapper.fromEntityToDTOList(getTicketDetailsForUser(orderId, authentication));
    }

    @Transactional
    public void deleteTickets(String orderId) {
        ticketRepository.deleteAllByOrderId(orderId);
    }

    @Transactional
    public void bookTickets(String orderId, Authentication authentication) {
        List<TicketEntity> tickets = getTicketDetailsForUser(orderId, authentication);
        SessionEntity session = tickets.get(0).getSession();
        Instant now = Instant.now(clock);
        Instant endTransactionTime = session.getStartTime().minusSeconds(
                fromMinutesToSeconds(bookingTimeRestrictionBeforeFilmStartsMin)
        );
        if (endTransactionTime.isBefore(now)) {
            throw new SessionHasAlreadyStartedException("Session will start in few minutes, you cannot book");
        }
        ticketRepository.saveAll(tickets.stream()
                .peek(el -> el.setTransactionEndTimestamp(endTransactionTime))
                .toList());
    }

    private List<TicketEntity> getTicketDetailsForUser(String orderId, Authentication authentication) {
        List<TicketEntity> tickets = ticketRepository.findByOrderId(orderId);
        if (tickets.isEmpty()) {
            throw new ObjectNotExistsException("Ticket with following order id does not exist");
        }
        if (!tickets.get(0).getUser().getEmail().equals(authentication.getPrincipal())) {
            throw new TicketDoesNotBelongToUserException("This ticket does not belong to current user");
        }
        return tickets;
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
