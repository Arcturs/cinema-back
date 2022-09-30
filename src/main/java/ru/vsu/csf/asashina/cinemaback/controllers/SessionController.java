package ru.vsu.csf.asashina.cinemaback.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.csf.asashina.cinemaback.models.dtos.session.SessionPageDTO;
import ru.vsu.csf.asashina.cinemaback.models.dtos.PagingDTO;
import ru.vsu.csf.asashina.cinemaback.models.request.SessionRequest;
import ru.vsu.csf.asashina.cinemaback.services.SessionService;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@AllArgsConstructor
@RequestMapping("/session")
public class SessionController {

    private final SessionService sessionService;

    @GetMapping("")
    public ResponseEntity<?> getAllFreshSessions(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
            @RequestParam(value = "size", required = false, defaultValue = "5") Integer size) {
        Page<SessionPageDTO> pages = sessionService.getAllFreshSessions(pageNumber - 1, size);
        return ResponseBuilder.build(new PagingDTO(pageNumber, size, pages.getTotalPages()),
                pages.getContent());
    }

    @GetMapping("/{sessionId}")
    public ResponseEntity<?> getSessionById(@PathVariable("sessionId") Long sessionId) {
        return ResponseBuilder.build(OK, sessionService.getSessionById(sessionId));
    }

    @PostMapping("")
    public ResponseEntity<?> createSession(@RequestBody @Valid SessionRequest request) {
        sessionService.createSession(request);
        return ResponseEntity.status(CREATED).build();
    }

    @PutMapping("/{sessionId}")
    public ResponseEntity<?> updateSession(
            @PathVariable("sessionId") Long sessionId,
            @RequestBody @Valid SessionRequest request
    ) {
        return ResponseBuilder.build(OK, sessionService.updateSession(sessionId, request));
    }

    @DeleteMapping("/{sessionId}")
    public ResponseEntity<?> deleteSession(@PathVariable("sessionId") Long sessionId) {
        sessionService.deleteSession(sessionId);
        return ResponseEntity.noContent().build();
    }

    //TODO: CRUD
}
