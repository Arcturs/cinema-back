package ru.vsu.csf.asashina.cinemaback.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.vsu.csf.asashina.cinemaback.exceptions.ObjectNotExistsException;
import ru.vsu.csf.asashina.cinemaback.exceptions.PosterException;
import ru.vsu.csf.asashina.cinemaback.exceptions.SessionsExistForMovieException;
import ru.vsu.csf.asashina.cinemaback.mappers.MovieMapper;
import ru.vsu.csf.asashina.cinemaback.models.dtos.movie.MovieDTO;
import ru.vsu.csf.asashina.cinemaback.models.dtos.movie.MoviePageDTO;
import ru.vsu.csf.asashina.cinemaback.models.entities.MovieEntity;
import ru.vsu.csf.asashina.cinemaback.models.enumerations.MovieSortEnum;
import ru.vsu.csf.asashina.cinemaback.models.request.MovieRequest;
import ru.vsu.csf.asashina.cinemaback.repositories.MovieRepository;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieService {

    private final MovieRepository movieRepository;

    private final MovieMapper movieMapper;

    @Value("${poster.directory}")
    private String posterDirectoryPath;

    public Page<MoviePageDTO> getMoviesInPages(Integer pageNumber, Integer size, String title, MovieSortEnum sort,
                                               Boolean isAsc) {
        var pageable = PageRequest.of(pageNumber, size, getSort(sort, isAsc));
        Page<MovieEntity> pages = movieRepository.getMoviesInPage(title, pageable);
        return pages.map(movieMapper::toPageDTOFromEntity);
    }

    public MovieDTO getMovieById(Long movieId) {
        MovieEntity entity = findMovieById(movieId);
        return movieMapper.toDTOFromEntity(entity);
    }

    public File getFileFromSystem(Long movieId) {
        findMovieById(movieId);
        return new File(posterDirectoryPath.concat("/").concat(Long.toString(movieId)));
    }

    @Transactional
    public void createMovie(MovieRequest request) throws IOException {
        var multipartFile = request.getPoster();
        if (multipartFile == null || multipartFile.isEmpty()) {
            throw new PosterException("You cannot upload empty file");
        }
        checkPoster(multipartFile);

        MovieEntity entity = movieRepository.save(movieMapper.toEntityFromRequest(request));
        String filePath = posterDirectoryPath.concat("/").concat(Long.toString(entity.getMovieId()));
        File directory = new File(filePath);
        request.getPoster().transferTo(directory);
        movieRepository.savePoster(entity.getMovieId(), filePath);
    }

    @Transactional
    public void deleteMovie(Long movieId) {
        movieRepository.deleteById(movieId);
    }

    @Transactional
    public MovieDTO updateMovie(MovieRequest request, Long movieId) throws IOException {
        MovieEntity entity = findMovieById(movieId);
        if (entity.getSessions() != null || !entity.getSessions().isEmpty()) {
            throw new SessionsExistForMovieException("Can not update movie due to existing sessions");
        }

        var multipartFile = request.getPoster();
        movieMapper.updateMovie(request, entity);
        MovieDTO movieDTO = movieMapper.toDTOFromEntity(movieRepository.save(entity));
        if (multipartFile != null && !multipartFile.isEmpty()) {
            checkPoster(multipartFile);
            String filePath = posterDirectoryPath.concat("/").concat(Long.toString(entity.getMovieId()));
            File directory = new File(filePath);
            multipartFile.transferTo(directory);
            movieRepository.updatePoster(entity.getMovieId(), filePath);
        }
        return movieDTO;
    }

    private Sort getSort(MovieSortEnum sort, Boolean isAsc) {
        Sort pageSort = switch (sort) {
            case BY_TITLE -> Sort.by("title");
            case BY_RATING -> Sort.by("rating");
            case BY_DURATION -> Sort.by("duration");
        };
        if (isAsc) {
            return pageSort.ascending();
        }
        return pageSort.descending();
    }

    @PostConstruct
    private void checkIsPathValid() {
        try {
            Paths.get(posterDirectoryPath);
        } catch (InvalidPathException e) {
            log.error(e.getMessage(), e);
        }
    }

    private void checkPoster(MultipartFile multipartFile) throws IOException {
        if (!Objects.requireNonNull(multipartFile.getOriginalFilename()).contains(".png") && !multipartFile.getOriginalFilename().contains(".jpg") &&
                !multipartFile.getOriginalFilename().contains(".jpeg")) {
            throw new PosterException("File type should be png, jpg or jpeg");
        }

        ByteArrayInputStream in = new ByteArrayInputStream(multipartFile.getBytes());
        BufferedImage image = ImageIO.read(in);
        if (image.getHeight() < 490 || image.getWidth() < 350) {
            throw new PosterException("Size proportions should be at least H:490 and W:350");
        }
    }

    private MovieEntity findMovieById(Long movieId) {
        return movieRepository.findById(movieId).orElseThrow(
                () -> new ObjectNotExistsException("Movie with following id does not exist")
        );
    }
}
