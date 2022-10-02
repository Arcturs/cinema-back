package ru.vsu.csf.asashina.cinemaback.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import ru.vsu.csf.asashina.cinemaback.models.dtos.SeatDTO;
import ru.vsu.csf.asashina.cinemaback.models.dtos.TicketDTO;
import ru.vsu.csf.asashina.cinemaback.models.dtos.UserDTO;
import ru.vsu.csf.asashina.cinemaback.models.entities.RoleEntity;
import ru.vsu.csf.asashina.cinemaback.models.entities.SeatEntity;
import ru.vsu.csf.asashina.cinemaback.models.entities.TicketEntity;
import ru.vsu.csf.asashina.cinemaback.models.entities.UserEntity;
import ru.vsu.csf.asashina.cinemaback.models.request.SignUpForm;

import java.util.Set;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    SessionMapper sessionMapper = Mappers.getMapper(SessionMapper.class);

    UserEntity fromRequestToEntity(SignUpForm signUpForm, String passwordHash, Set<RoleEntity> roles);

    @Mapping(target = "tickets", expression = "java(ticketsMapperDTO(entity.getTickets()))")
    UserDTO fromEntityToDTO(UserEntity entity);

    @Mappings({
            @Mapping(target = "session", expression = "java(sessionMapper.toDTOFromEntity(entity.getSession()))"),
            @Mapping(target = "seat", expression = "java(seatMapperDTO(entity.getSeat()))")
    })
    Set<TicketDTO> ticketsMapperDTO(Set<TicketEntity> entities);

    SeatDTO seatMapperDTO(SeatEntity entity);
}
