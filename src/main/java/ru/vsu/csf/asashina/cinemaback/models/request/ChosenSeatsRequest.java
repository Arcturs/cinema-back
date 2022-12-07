package ru.vsu.csf.asashina.cinemaback.models.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChosenSeatsRequest {

    @NotNull
    Long sessionId;

    List<Long> seatPlanForSessionIds;
}
