package ru.vsu.csf.asashina.cinemaback.models.dtos.session;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class SessionPageDTO {

    private Long sessionId;

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm", timezone = "GMT")
    private Instant startTime;

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm", timezone = "GMT")
    private Instant endTime;

    private Integer price;
    private ScreenDTO screen;
    private MovieDTO movie;
}
