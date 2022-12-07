package ru.vsu.csf.asashina.cinemaback.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import ru.vsu.csf.asashina.cinemaback.models.dtos.session.SessionPageDTO;
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

    SeatMapper seatMapper = Mappers.getMapper(SeatMapper.class);

    ScreenPageDTO toPageDTOFromEntity(ScreenEntity entity);

    @Mapping(target = "seatsSet", expression = "java(seatMapper.fromEntityToDTOSet(entity.getSeatsSet()))")
    ScreenDTO toDTOFromEntity(ScreenEntity entity);

    ScreenEntity fromRequestToEntity(ScreenRequest request, Integer screenNumber);

    @Mapping(target = "seatsSet", expression = "java(seatMapper.fromDTOToEntitySet(dto.getSeatsSet()))")
    ScreenEntity fromDTOToEntity(ScreenDTO dto);
}
