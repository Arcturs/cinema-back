package ru.vsu.csf.asashina.cinemaback.configurations;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.vsu.csf.asashina.cinemaback.filters.AuthorizationFilter;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration implements WebMvcConfigurer {

    private final static String USER = "USER";
    private final static String ADMIN = "ADMIN";

    private final AuthorizationFilter authorizationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests()
                .antMatchers(GET, "/user/**", "/order/*").hasAnyAuthority(USER)
                .antMatchers(POST, "/user/refresh-token", "/order").hasAnyAuthority(USER)

                .antMatchers(GET, "/movie", "/movie/**", "/screen", "/screen/*", "/session",
                        "/session/*").permitAll()
                .antMatchers(POST, "/user/**").permitAll()

                .antMatchers(POST, "/movie", "/screen", "/session").hasAnyAuthority(ADMIN)
                .antMatchers(PUT, "/movie/**", "/session/**").hasAnyAuthority(ADMIN)
                .antMatchers(DELETE, "/movie/**", "/session/**").hasAnyAuthority(ADMIN)
                .anyRequest().permitAll();

        http.addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers(
                        "/swagger-resources/**",
                        "/swagger-ui.html",
                        "/swagger-ui/**",
                        "/api/v1/swagger-config",
                        "/openapi.yaml",
                        "/webjars/**"
                );
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("*");
    }
}
