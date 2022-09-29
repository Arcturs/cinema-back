package ru.vsu.csf.asashina.cinemaback.models.dtos.movie;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MovieDTO {

    private Long movieId;
    private String description;
    private LocalTime duration;
    private String title;
    private Double rating;
}
