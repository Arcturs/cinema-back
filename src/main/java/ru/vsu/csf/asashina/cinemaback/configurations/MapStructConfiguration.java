package ru.vsu.csf.asashina.cinemaback.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.vsu.csf.asashina.cinemaback.mappers.MovieMapper;
import ru.vsu.csf.asashina.cinemaback.mappers.ScreenMapper;
import ru.vsu.csf.asashina.cinemaback.mappers.SessionMapper;

@Configuration
public class MapStructConfiguration {

    @Bean
    public MovieMapper movieMapper() {
        return MovieMapper.INSTANCE;
    }

    @Bean
    public ScreenMapper screenMapper() {
        return ScreenMapper.INSTANCE;
    }

    @Bean
    SessionMapper sessionMapper() {
        return SessionMapper.INSTANCE;
    }
}
