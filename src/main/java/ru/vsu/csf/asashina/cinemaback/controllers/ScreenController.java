package ru.vsu.csf.asashina.cinemaback.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.csf.asashina.cinemaback.models.dtos.PagingDTO;
import ru.vsu.csf.asashina.cinemaback.models.dtos.screen.ScreenPageDTO;
import ru.vsu.csf.asashina.cinemaback.models.request.ScreenRequest;
import ru.vsu.csf.asashina.cinemaback.services.ScreenService;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@AllArgsConstructor
@RequestMapping("/screen")
public class ScreenController {

    private final ScreenService screenService;

    @GetMapping("")
    public ResponseEntity<?> getScreens(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
            @RequestParam(value = "size", required = false, defaultValue = "5") Integer size) {
        Page<ScreenPageDTO> pages = screenService.getScreenInPages(pageNumber, size);
        return ResponseBuilder.build(new PagingDTO(pageNumber, size, pages.getTotalPages()),
                pages.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getScreenById(@PathVariable("id") Long screenId) {
        return ResponseBuilder.build(OK, screenService.getScreenById(screenId));
    }

    @PostMapping("")
    public ResponseEntity<?> createScreen(@RequestBody @Valid ScreenRequest request) {
        screenService.createScreen(request);
        return ResponseEntity.status(CREATED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteScreen(@PathVariable("id") Long screenId) {
        screenService.deleteScreen(screenId);
        return ResponseEntity.noContent().build();
    }
}
