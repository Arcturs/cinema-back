package ru.vsu.csf.asashina.cinemaback.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import ru.vsu.csf.asashina.cinemaback.models.dtos.session.SessionDTO;
import ru.vsu.csf.asashina.cinemaback.models.dtos.session.SessionPageDTO;
import ru.vsu.csf.asashina.cinemaback.models.entities.*;
import ru.vsu.csf.asashina.cinemaback.models.request.SessionRequest;

import java.time.Instant;

@Mapper
public interface SessionMapper {

    SessionMapper INSTANCE = Mappers.getMapper(SessionMapper.class);

    MovieMapper movieMapper = Mappers.getMapper(MovieMapper.class);
    ScreenMapper screenMapper = Mappers.getMapper(ScreenMapper.class);
    SeatPlanMapper seatPlanMapper = Mappers.getMapper(SeatPlanMapper.class);

    @Mappings({
            @Mapping(target = "movie", expression = "java(movieMapper.toDTOFromEntity(entity.getMovie()))"),
            @Mapping(target = "screen", expression = "java(screenMapper.toDTOFromEntity(entity.getScreen()))")
    })
    SessionPageDTO toPageDTOFromEntity(SessionEntity entity);

    @Mappings({
            @Mapping(target = "movie", expression = "java(movieMapper.toDTOFromEntity(entity.getMovie()))"),
            @Mapping(target = "screen", expression = "java(screenMapper.toDTOFromEntity(entity.getScreen()))"),
            @Mapping(target = "seatPlan", expression = "java(seatPlanMapper.fromEntityToDTOSet(entity.getSeatPlan()))")
    })
    SessionDTO toDTOFromEntity(SessionEntity entity);

    @Mappings({
            @Mapping(target = "startTime", source = "start"),
            @Mapping(target = "endTime", source = "end")
    })
    SessionEntity fromRequestToEntity(SessionRequest request, Instant start, Instant end, MovieEntity movie,
                                      ScreenEntity screen);

    @Mappings({
            @Mapping(target = "movie", expression = "java(movieMapper.toEntityFromDTO(sessionPageDTO.getMovie()))"),
            @Mapping(target = "screen", expression = "java(screenMapper.fromDTOToEntity(sessionPageDTO.getScreen()))")
    })
    SessionEntity fromDTOToEntity(SessionPageDTO sessionPageDTO);

    @Mappings({
            @Mapping(target = "startTime", source = "start"),
            @Mapping(target = "endTime", source = "end")
    })
    void updateSessionEntity(@MappingTarget SessionEntity entity, SessionRequest request, Instant start, Instant end,
                             MovieEntity movie, ScreenEntity screen);
}
