package ru.vsu.csf.asashina.cinemaback.models.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "seat")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SeatEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatId;

    @Column(nullable = false)
    private Integer row;

    @Column(nullable = false)
    private Integer seatNumber;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "screen_id")
    private ScreenEntity screen;
}
