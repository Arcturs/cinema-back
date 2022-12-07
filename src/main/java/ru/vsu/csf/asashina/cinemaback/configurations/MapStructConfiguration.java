package ru.vsu.csf.asashina.cinemaback.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.vsu.csf.asashina.cinemaback.mappers.*;

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
    public SessionMapper sessionMapper() {
        return SessionMapper.INSTANCE;
    }

    @Bean
    public UserMapper userMapper() {
        return UserMapper.INSTANCE;
    }

    @Bean
    public SeatPlanMapper seatPlanMapper() {
        return SeatPlanMapper.INSTANCE;
    }

    @Bean
    public SeatMapper seatMapper() {
        return SeatMapper.INSTANCE;
    }

    @Bean
    public TicketMapper ticketMapper() {
        return TicketMapper.INSTANCE;
    }
}
