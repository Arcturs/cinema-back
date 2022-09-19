package ru.vsu.csf.asashina.cinemaback.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import ru.vsu.csf.asashina.cinemaback.models.SessionDTO;
import ru.vsu.csf.asashina.cinemaback.models.dtos.movie.MovieDTO;
import ru.vsu.csf.asashina.cinemaback.models.dtos.screen.ScreenDTO;
import ru.vsu.csf.asashina.cinemaback.models.entities.MovieEntity;
import ru.vsu.csf.asashina.cinemaback.models.entities.ScreenEntity;
import ru.vsu.csf.asashina.cinemaback.models.entities.SessionEntity;

@Mapper
public interface SessionMapper {

    SessionMapper INSTANCE = Mappers.getMapper(SessionMapper.class);

    @Mappings({
            @Mapping(target = "movie", expression = "java(movieMapper(entity.getMovie()))"),
            @Mapping(target = "screen", expression = "java(screenMapper(entity.getScreen()))")
    })
    SessionDTO toDTOFromEntity(SessionEntity entity);

    MovieDTO movieMapper(MovieEntity entity);

    ScreenDTO screenMapper(ScreenEntity entity);
}
