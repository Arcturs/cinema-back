package ru.vsu.csf.asashina.cinemaback.models.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ScreenRequest {

    @NotNull
    @Min(1)
    @Max(16)
    private Integer rows;

    @NotNull
    @Min(1)
    @Max(20)
    private Integer seats;
}
