package ru.vsu.csf.asashina.cinemaback.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import ru.vsu.csf.asashina.cinemaback.models.SessionDTO;
import ru.vsu.csf.asashina.cinemaback.models.dtos.SeatDTO;
import ru.vsu.csf.asashina.cinemaback.models.dtos.screen.ScreenDTO;
import ru.vsu.csf.asashina.cinemaback.models.dtos.screen.ScreenPageDTO;
import ru.vsu.csf.asashina.cinemaback.models.entities.ScreenEntity;
import ru.vsu.csf.asashina.cinemaback.models.entities.SeatEntity;
import ru.vsu.csf.asashina.cinemaback.models.entities.SessionEntity;
import ru.vsu.csf.asashina.cinemaback.models.request.ScreenRequest;

import java.util.Set;

@Mapper
public interface ScreenMapper {

    ScreenMapper INSTANCE = Mappers.getMapper(ScreenMapper.class);

    ScreenPageDTO toPageDTOFromEntity(ScreenEntity entity);

    @Mappings({
            @Mapping(target = "seatsSet", expression = "java(seatDTOMapping(entity.getSeatsSet()))"),
            @Mapping(target = "sessions", expression = "java(sessionDTOMapping(entity.getSessions()))")
    })
    ScreenDTO toDTOFromEntity(ScreenEntity entity);

    Set<SeatDTO> seatDTOMapping(Set<SeatEntity> set);

    Set<SessionDTO> sessionDTOMapping(Set<SessionEntity> set);

    ScreenEntity fromRequestToEntity(ScreenRequest request, Integer screenNumber);
}
