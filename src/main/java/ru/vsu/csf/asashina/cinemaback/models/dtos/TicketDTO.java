package ru.vsu.csf.asashina.cinemaback.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.vsu.csf.asashina.cinemaback.models.dtos.session.SessionDTO;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
//TODO: Order Entity with ticket + UUID id + is_paid
public class TicketDTO {

    private Long ticketId;
    private SessionDTO session;
    private SeatDTO seat;
}
