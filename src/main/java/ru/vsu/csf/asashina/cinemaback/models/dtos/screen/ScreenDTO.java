package ru.vsu.csf.asashina.cinemaback.models.dtos.screen;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.vsu.csf.asashina.cinemaback.models.dtos.SeatDTO;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ScreenDTO {

    private Long screenId;
    private Integer screenNumber;
    private Integer rows;
    private Integer seats;
    private Set<SeatDTO> seatsSet = new HashSet<>();
}
