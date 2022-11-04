package ru.vsu.csf.asashina.cinemaback.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.vsu.csf.asashina.cinemaback.models.entities.MovieEntity;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, Long> {

    @Query(value = """
                    SELECT *
                    FROM movie
                    WHERE LOWER(title) LIKE CONCAT('%', LOWER(:title), '%')
                    """, nativeQuery = true)
    Page<MovieEntity> getMoviesInPage(@Param("title") String title, Pageable pageable);

    @Modifying
    @Query(value = "INSERT INTO poster(movie_id, poster_path) VALUES(:id, :path)", nativeQuery = true)
    void savePoster(@Param("id") Long movieId,
                    @Param("path") String path);

    @Modifying
    @Query(value = "UPDATE poster SET poster_path = :path WHERE movie_id = :id", nativeQuery = true)
    void updatePoster(@Param("id") Long movieId,
                      @Param("path") String path);
}
