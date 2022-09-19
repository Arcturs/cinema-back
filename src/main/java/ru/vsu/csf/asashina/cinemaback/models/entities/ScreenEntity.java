package ru.vsu.csf.asashina.cinemaback.models.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "screen")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ScreenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long screenId;

    @Column(nullable = false, unique = true)
    private Integer screenNumber;

    @Column(nullable = false)
    private Integer rows;

    @Column(nullable = false)
    private Integer seats;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE, mappedBy = "screen")
    private Set<SeatEntity> seatsSet = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE, mappedBy = "screen")
    private Set<SessionEntity> sessions = new HashSet<>();
}
