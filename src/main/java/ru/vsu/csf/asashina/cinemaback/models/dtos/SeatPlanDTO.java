package ru.vsu.csf.asashina.cinemaback.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SeatPlanDTO {

    private Long seatPlanForSessionId;
    private SeatDTO seat;
    private Boolean isAvailable = true;
}
