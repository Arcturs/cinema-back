package ru.vsu.csf.asashina.cinemaback.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vsu.csf.asashina.cinemaback.models.request.ChosenSeatsRequest;
import ru.vsu.csf.asashina.cinemaback.services.OrderService;

import java.util.Map;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/order")
@AllArgsConstructor
public class OrderController {

    private static final String ORDER_ID = "orderId";

    private final OrderService orderService;

    @PostMapping()
    public ResponseEntity<?> bookChosenSeat(@RequestBody ChosenSeatsRequest request,
                                            Authentication authentication) {
        return ResponseBuilder.build(OK,
                Map.of(ORDER_ID, orderService.bookSeatsBeforeProceeding(request, authentication))
        );
    }
}
