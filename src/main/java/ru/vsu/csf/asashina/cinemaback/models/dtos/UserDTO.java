package ru.vsu.csf.asashina.cinemaback.models.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.vsu.csf.asashina.cinemaback.models.entities.RoleEntity;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

    private Long userId;
    private String email;
    private String name;
    private String surname;

    @JsonIgnore
    private String passwordHash;

    private Set<RoleEntity> roles = new HashSet<>();

    private Set<TicketDTO> tickets = new HashSet<>();
}
