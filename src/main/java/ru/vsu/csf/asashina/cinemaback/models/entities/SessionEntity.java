package ru.vsu.csf.asashina.cinemaback.models.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "session")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SessionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sessionId;

    @Column(nullable = false)
    private Instant startTime;

    @Column(nullable = false)
    private Instant endTime;

    @Column(nullable = false)
    private Integer price;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "screen_id")
    private ScreenEntity screen;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "movie_id")
    private MovieEntity movie;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "session")
    private Set<SeatPlanEntity> seatPlan = new HashSet<>();
}
