package ru.vsu.csf.asashina.cinemaback.services;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.csf.asashina.cinemaback.models.dtos.SeatPlanDTO;
import ru.vsu.csf.asashina.cinemaback.models.dtos.TicketDTO;
import ru.vsu.csf.asashina.cinemaback.models.dtos.UserDTO;
import ru.vsu.csf.asashina.cinemaback.models.dtos.session.SessionPageDTO;
import ru.vsu.csf.asashina.cinemaback.models.request.ChosenSeatsRequest;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {

    private final SeatPlanService seatPlanService;
    private final UserService userService;
    private final SessionService sessionService;
    private final TicketService ticketService;

    @Transactional
    public String bookSeatsBeforeProceeding(ChosenSeatsRequest request, Authentication authentication) {
        SessionPageDTO session = sessionService.validateIsSessionStarted(request.getSessionId());
        List<Long> chosenSeatsOnSeatPlanIds = request.getSeatPlanForSessionIds();
        List<SeatPlanDTO> seatPlan = seatPlanService.bookSeatsForProceeding(chosenSeatsOnSeatPlanIds,
                request.getSessionId());
        UserDTO user = userService.findUserByEmail((String) authentication.getPrincipal());
        return ticketService.createNewOrder(seatPlan, session, user);
    }

    @Transactional
    public void cancelOrder(String orderId, Authentication authentication) {
        List<TicketDTO> tickets = ticketService.getTicketDetails(orderId, authentication);
        Long sessionId = tickets.get(0).getSession().getSessionId();
        List<Long> seatIds = tickets.stream()
                .map(el -> el.getSeat().getSeatId())
                .toList();
        seatPlanService.freeSeats(seatIds, sessionId);
        ticketService.deleteTickets(orderId);
    }
}
