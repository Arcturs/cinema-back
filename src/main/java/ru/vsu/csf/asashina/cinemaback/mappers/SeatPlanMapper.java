package ru.vsu.csf.asashina.cinemaback.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.vsu.csf.asashina.cinemaback.models.dtos.SeatPlanDTO;
import ru.vsu.csf.asashina.cinemaback.models.entities.SeatPlanEntity;

import java.util.List;
import java.util.Set;

@Mapper
public interface SeatPlanMapper {

    SeatPlanMapper INSTANCE = Mappers.getMapper(SeatPlanMapper.class);

    SeatMapper seatMapper = Mappers.getMapper(SeatMapper.class);

    @Mapping(target = "seat", expression = "java(seatMapper.fromEntityToDTO(entity.getSeat()))")
    List<SeatPlanDTO> fromEntityToDTOList(List<SeatPlanEntity> entity);

    @Mapping(target = "seat", expression = "java(seatMapper.fromEntityToDTO(entity.getSeat()))")
    Set<SeatPlanDTO> fromEntityToDTOSet(Set<SeatPlanEntity> entity);
}
