package ru.vsu.csf.asashina.cinemaback.models.dtos.session;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.vsu.csf.asashina.cinemaback.models.dtos.SeatPlanDTO;
import ru.vsu.csf.asashina.cinemaback.models.dtos.movie.MovieDTO;
import ru.vsu.csf.asashina.cinemaback.models.dtos.screen.ScreenDTO;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

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
    private Set<SeatPlanDTO> seatPlan = new HashSet<>();
}
