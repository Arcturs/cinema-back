package ru.vsu.csf.asashina.cinemaback.controllers;

import lombok.AllArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.csf.asashina.cinemaback.models.dtos.session.SessionPageDTO;
import ru.vsu.csf.asashina.cinemaback.models.dtos.PagingDTO;
import ru.vsu.csf.asashina.cinemaback.models.dtos.movie.MoviePageDTO;
import ru.vsu.csf.asashina.cinemaback.models.enumerations.MovieSortEnum;
import ru.vsu.csf.asashina.cinemaback.models.request.MovieRequest;
import ru.vsu.csf.asashina.cinemaback.services.MovieService;
import ru.vsu.csf.asashina.cinemaback.services.SessionService;

import javax.validation.Valid;
import java.io.IOException;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@AllArgsConstructor
@RequestMapping("/movie")
public class MovieController {

    private final MovieService movieService;
    private final SessionService sessionService;

    @GetMapping
    public ResponseEntity<?> getMoviesInPages(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
            @RequestParam(value = "size", required = false, defaultValue = "5") Integer size,
            @RequestParam(value = "title", required = false, defaultValue = "") String title,
            @RequestParam(value = "sort", required = false, defaultValue = "BY_RATING") MovieSortEnum sort,
            @RequestParam(value = "isAsc", required = false, defaultValue = "true") Boolean isAsc) {
        Page<MoviePageDTO> pages = movieService.getMoviesInPages(pageNumber, size, title, sort, isAsc);
        return ResponseBuilder.build(new PagingDTO(pageNumber, size, pages.getTotalPages()),
                pages.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMovieById(@PathVariable("id") Long movieId) {
        return ResponseBuilder.build(OK, movieService.getMovieById(movieId));
    }

    @GetMapping(value = "/{id}/poster", produces = IMAGE_PNG_VALUE)
    public ResponseEntity<?> getMoviePosterById(@PathVariable("id") Long movieId) throws IOException {
        FileSystemResource in = new FileSystemResource(movieService.getFileFromSystem(movieId));
        var headers = new HttpHeaders();
        headers.setContentLength(in.contentLength());
        return new ResponseEntity(in, headers, OK);
    }

    @GetMapping("/{id}/sessions")
    public ResponseEntity<?> getSessionsOnMovie(
            @PathVariable("id") Long movieId,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
            @RequestParam(value = "size", required = false, defaultValue = "5") Integer size) {
        Page<SessionPageDTO> pages = sessionService.getAllFreshSessionsByMovieId(movieId, pageNumber, size);
        return ResponseBuilder.build(new PagingDTO(pageNumber, size, pages.getTotalPages()),
                pages.getContent());
    }

    @PostMapping(value = "", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createMovie(@ModelAttribute @Valid MovieRequest request) throws IOException {
        movieService.createMovie(request);
        return ResponseEntity.status(CREATED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMovieById(@PathVariable("id") Long movieId) {
        movieService.deleteMovie(movieId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateMovieById(@PathVariable("id") Long movieId,
                                             @ModelAttribute @Valid MovieRequest request) throws IOException {
        return ResponseBuilder.build(OK, movieService.updateMovie(request, movieId));
    }
}
