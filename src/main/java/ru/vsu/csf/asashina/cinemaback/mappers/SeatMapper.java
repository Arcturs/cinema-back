package ru.vsu.csf.asashina.cinemaback.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.vsu.csf.asashina.cinemaback.models.dtos.SeatDTO;
import ru.vsu.csf.asashina.cinemaback.models.entities.SeatEntity;

import java.util.Set;

@Mapper
public interface SeatMapper {

    SeatMapper INSTANCE = Mappers.getMapper(SeatMapper.class);

    Set<SeatDTO> fromEntityToDTOSet(Set<SeatEntity> set);

    Set<SeatEntity> fromDTOToEntitySet(Set<SeatDTO> set);

    SeatDTO fromEntityToDTO(SeatEntity entity);

    SeatEntity fromDTOToEntity(SeatDTO dto);
}
