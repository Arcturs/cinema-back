package ru.vsu.csf.asashina.cinemaback.models.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "user_refresh_token")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RefreshTokenEntity {

    @Id
    private String refreshToken;

    @Column(nullable = false)
    private Instant dateExpire;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
