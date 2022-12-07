package ru.vsu.csf.asashina.cinemaback.models.dtos.session;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.vsu.csf.asashina.cinemaback.models.dtos.SeatPlanDTO;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SessionDTO extends SessionPageDTO{

    private Set<SeatPlanDTO> seatPlan = new HashSet<>();
}
