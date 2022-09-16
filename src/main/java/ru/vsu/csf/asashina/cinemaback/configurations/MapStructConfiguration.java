package ru.vsu.csf.asashina.cinemaback.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.vsu.csf.asashina.cinemaback.mappers.MovieMapper;

@Configuration
public class MapStructConfiguration {

    @Bean
    public MovieMapper movieMapper() {
        return MovieMapper.INSTANCE;
    }
}
