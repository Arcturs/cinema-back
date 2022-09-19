package ru.vsu.csf.asashina.cinemaback.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.vsu.csf.asashina.cinemaback.models.SessionDTO;
import ru.vsu.csf.asashina.cinemaback.models.dtos.PagingDTO;
import ru.vsu.csf.asashina.cinemaback.services.SessionService;

@RestController
@AllArgsConstructor
@RequestMapping("/session")
public class SessionController {

    private final SessionService sessionService;

    @GetMapping("")
    public ResponseEntity<?> getAllFreshSessions(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
            @RequestParam(value = "size", required = false, defaultValue = "5") Integer size) {
        Page<SessionDTO> pages = sessionService.getAllFreshSessions(pageNumber, size);
        return ResponseBuilder.build(new PagingDTO(pageNumber, size, pages.getTotalPages()),
                pages.getContent());
    }

    //TODO: CRUD
    //TODO: ситинг план для сеанса
}
