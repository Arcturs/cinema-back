package ru.vsu.csf.asashina.cinemaback.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.vsu.csf.asashina.cinemaback.models.dtos.screen.ScreenDTO;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SeatDTO {

    private Long seatId;
    private Integer row;
    private Integer seatNumber;
    private ScreenDTO screen;
}
