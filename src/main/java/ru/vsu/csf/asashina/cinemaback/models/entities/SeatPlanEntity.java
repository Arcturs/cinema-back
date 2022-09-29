package ru.vsu.csf.asashina.cinemaback.models.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "seat_plan_for_session")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SeatPlanEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatPlanForSessionId;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "session_id")
    private SessionEntity session;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "seat_id")
    private SeatEntity seat;

    @Column(nullable = false)
    private Boolean isAvailable = true;

    public SeatPlanEntity(SessionEntity session, SeatEntity seat) {
        this.session = session;
        this.seat = seat;
        this.isAvailable = true;
    }
}
