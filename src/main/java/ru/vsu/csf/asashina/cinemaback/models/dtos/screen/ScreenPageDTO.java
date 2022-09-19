package ru.vsu.csf.asashina.cinemaback.models.dtos.screen;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ScreenPageDTO {

    private Long screenId;
    private Integer screenNumber;
    private Integer rows;
    private Integer seats;
}
