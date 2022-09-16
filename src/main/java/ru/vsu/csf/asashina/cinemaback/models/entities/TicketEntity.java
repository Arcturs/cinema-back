package ru.vsu.csf.asashina.cinemaback.models.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "ticket")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TicketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticketId;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "session_id")
    private SessionEntity session;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "seat_id")
    private SeatEntity seat;
}
