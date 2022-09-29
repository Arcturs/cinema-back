package ru.vsu.csf.asashina.cinemaback.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import ru.vsu.csf.asashina.cinemaback.models.dtos.movie.MovieDTO;
import ru.vsu.csf.asashina.cinemaback.models.dtos.movie.MoviePageDTO;
import ru.vsu.csf.asashina.cinemaback.models.entities.MovieEntity;
import ru.vsu.csf.asashina.cinemaback.models.request.MovieRequest;

@Mapper
public interface MovieMapper {

    MovieMapper INSTANCE = Mappers.getMapper(MovieMapper.class);

    MoviePageDTO toPageDTOFromEntity(MovieEntity entity);

    MovieDTO toDTOFromEntity(MovieEntity entity);

    MovieEntity toEntityFromRequest(MovieRequest request);

    void updateMovie(MovieRequest request, @MappingTarget MovieEntity entity);
}
