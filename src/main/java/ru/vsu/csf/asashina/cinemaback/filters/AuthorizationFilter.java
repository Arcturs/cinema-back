package ru.vsu.csf.asashina.cinemaback.filters;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@AllArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {

    public final static String AUTH_HEADER = "Bearer ";

    private final TokenFilter tokenFilter;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith(AUTH_HEADER)) {
            try {
                tokenFilter.authenticate(authHeader);
            } catch (Exception e) {
                tokenFilter.sendErrorMessage(response, e.getMessage());
            }
        }
        filterChain.doFilter(request, response);
    }
}

