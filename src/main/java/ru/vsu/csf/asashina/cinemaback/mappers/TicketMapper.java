package ru.vsu.csf.asashina.cinemaback.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import ru.vsu.csf.asashina.cinemaback.models.dtos.TicketDTO;
import ru.vsu.csf.asashina.cinemaback.models.entities.SeatEntity;
import ru.vsu.csf.asashina.cinemaback.models.entities.SessionEntity;
import ru.vsu.csf.asashina.cinemaback.models.entities.TicketEntity;
import ru.vsu.csf.asashina.cinemaback.models.entities.UserEntity;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Mapper
public interface TicketMapper {

    TicketMapper INSTANCE = Mappers.getMapper(TicketMapper.class);

    SessionMapper sessionMapper = Mappers.getMapper(SessionMapper.class);
    SeatMapper seatMapper = Mappers.getMapper(SeatMapper.class);

    @Mappings({
            @Mapping(target = "session", expression = "java(sessionMapper.toPageDTOFromEntity(entity.getSession()))"),
            @Mapping(target = "seat", expression = "java(seatMapper.fromEntityToDTO(entity.getSeat()))")
    })
    Set<TicketDTO> fromEntityToDTOSet(Set<TicketEntity> entities);

    @Mappings({
            @Mapping(target = "session", expression = "java(sessionMapper.fromDTOToEntity(dtos.getSession()))"),
            @Mapping(target = "seat", expression = "java(seatMapper.fromDTOToEntity(dtos.getSeat()))")
    })
    Set<TicketEntity> fromDTOToEntitySet(Set<TicketDTO> dtos);

    TicketEntity toEntity(UserEntity user, SessionEntity session, SeatEntity seat, String orderId, Boolean isPaid,
                          Instant transactionStartTimestamp, Instant transactionEndTimestamp);

    @Mappings({
            @Mapping(target = "session", expression = "java(sessionMapper.toPageDTOFromEntity(entity.getSession()))"),
            @Mapping(target = "seat", expression = "java(seatMapper.fromEntityToDTO(entity.getSeat()))")
    })
    List<TicketDTO> fromEntityToDTOList(List<TicketEntity> entities);
}
