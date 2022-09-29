package ru.vsu.csf.asashina.cinemaback.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SeatDTO {

    private Long seatId;
    private Integer row;
    private Integer seatNumber;
}
