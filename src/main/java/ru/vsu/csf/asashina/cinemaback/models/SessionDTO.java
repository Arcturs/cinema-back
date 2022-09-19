package ru.vsu.csf.asashina.cinemaback.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.vsu.csf.asashina.cinemaback.models.dtos.movie.MovieDTO;
import ru.vsu.csf.asashina.cinemaback.models.dtos.screen.ScreenDTO;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SessionDTO {

    private Long sessionId;
    private Instant startTime;
    private Instant endTime;
    private Integer price;
    private ScreenDTO screen;
    private MovieDTO movie;
}
