package ru.vsu.csf.asashina.cinemaback.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import ru.vsu.csf.asashina.cinemaback.models.dtos.SeatDTO;
import ru.vsu.csf.asashina.cinemaback.models.dtos.SeatPlanDTO;
import ru.vsu.csf.asashina.cinemaback.models.dtos.session.SessionDTO;
import ru.vsu.csf.asashina.cinemaback.models.dtos.session.SessionPageDTO;
import ru.vsu.csf.asashina.cinemaback.models.dtos.movie.MovieDTO;
import ru.vsu.csf.asashina.cinemaback.models.dtos.screen.ScreenDTO;
import ru.vsu.csf.asashina.cinemaback.models.entities.*;
import ru.vsu.csf.asashina.cinemaback.models.request.SessionRequest;

import java.time.Instant;
import java.util.Set;

@Mapper
public interface SessionMapper {

    SessionMapper INSTANCE = Mappers.getMapper(SessionMapper.class);

    @Mappings({
            @Mapping(target = "movie", expression = "java(movieMapper(entity.getMovie()))"),
            @Mapping(target = "screen", expression = "java(screenMapper(entity.getScreen()))")
    })
    SessionPageDTO toPageDTOFromEntity(SessionEntity entity);

    @Mappings({
            @Mapping(target = "movie", expression = "java(movieMapper(entity.getMovie()))"),
            @Mapping(target = "screen", expression = "java(screenMapper(entity.getScreen()))"),
            @Mapping(target = "seatPlan", expression = "java(seatPlanMapper(entity.getSeatPlan()))")
    })
    SessionDTO toDTOFromEntity(SessionEntity entity);

    @Mappings({
            @Mapping(target = "startTime", source = "start"),
            @Mapping(target = "endTime", source = "end")
    })
    SessionEntity fromRequestToEntity(SessionRequest request, Instant start, Instant end, MovieEntity movie,
                                      ScreenEntity screen);

    @Mappings({
            @Mapping(target = "startTime", source = "start"),
            @Mapping(target = "endTime", source = "end")
    })
    void updateSessionEntity(@MappingTarget SessionEntity entity, SessionRequest request, Instant start, Instant end,
                             MovieEntity movie, ScreenEntity screen);

    MovieDTO movieMapper(MovieEntity entity);

    ScreenDTO screenMapper(ScreenEntity entity);

    SeatDTO seatMapper(SeatEntity entity);

    @Mapping(target = "seat", expression = "java(seatMapper(entity.getSeat()))")
    Set<SeatPlanDTO> seatPlanMapper(Set<SeatPlanEntity> entity);
}
