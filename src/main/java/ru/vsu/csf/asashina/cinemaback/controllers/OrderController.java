package ru.vsu.csf.asashina.cinemaback.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.vsu.csf.asashina.cinemaback.models.request.ChosenSeatsRequest;
import ru.vsu.csf.asashina.cinemaback.services.OrderService;
import ru.vsu.csf.asashina.cinemaback.services.TicketService;

import java.util.Map;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/order")
@AllArgsConstructor
public class OrderController {

    private static final String ORDER_ID = "orderId";

    private final OrderService orderService;
    private final TicketService ticketService;

    @PostMapping
    public ResponseEntity<?> bookChosenSeat(@RequestBody ChosenSeatsRequest request,
                                            Authentication authentication) {
        return ResponseBuilder.build(OK,
                Map.of(ORDER_ID, orderService.bookSeatsBeforeProceeding(request, authentication))
        );
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderDetails(@PathVariable("orderId") String orderId,
                                             Authentication authentication) {
        return ResponseBuilder.build(OK, ticketService.getTicketDetails(orderId, authentication));
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<?> cancelOrder(@PathVariable("orderId") String orderId,
                                         Authentication authentication) {
        orderService.cancelOrder(orderId, authentication);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{orderId}/book")
    public ResponseEntity<?> bookOrder(@PathVariable("orderId") String orderId,
                                       Authentication authentication) {
        ticketService.bookTickets(orderId, authentication);
        return ResponseEntity.noContent().build();
    }
}
