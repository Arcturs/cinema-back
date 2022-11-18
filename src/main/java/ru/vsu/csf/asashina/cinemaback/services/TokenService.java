package ru.vsu.csf.asashina.cinemaback.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.csf.asashina.cinemaback.exceptions.ObjectNotExistsException;
import ru.vsu.csf.asashina.cinemaback.mappers.UserMapper;
import ru.vsu.csf.asashina.cinemaback.models.dtos.UserDTO;
import ru.vsu.csf.asashina.cinemaback.models.entities.RefreshTokenEntity;
import ru.vsu.csf.asashina.cinemaback.repositories.RefreshTokenRepository;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final UserService userService;

    private final RefreshTokenRepository refreshTokenRepository;

    private final UserMapper userMapper;

    private static final String ACCESS_TOKEN = "access_token";
    private static final String REFRESH_TOKEN = "refresh_token";

    private final Clock clock;

    @Value("${security.jwt.secretKey}")
    private String jwtKey;

    @Value("${security.jwt.expiresMs.accessToken}")
    private Integer accessTokenExpireTime;

    @Value("${security.jwt.expiresMs.refreshToken}")
    @Setter
    private Integer refreshTokenExpireTime;

    @Transactional
    public Map<String, String> createTokens(UserDTO user) {
        var algorithm = Algorithm.HMAC256(jwtKey.getBytes());
        return Map.of(
                ACCESS_TOKEN, createAccessToken(user, algorithm),
                REFRESH_TOKEN, createRefreshToken(user)
        );
    }

    public Map<String, Object> refreshToken(String refreshToken) {
        Map<String, Object> response = new HashMap<>();
        Algorithm algorithm = Algorithm.HMAC256(jwtKey.getBytes());
        UserDTO user = verifyRefreshToken(refreshToken);
        response.put("access_token", createAccessToken(user, algorithm));
        response.put("refresh_token", refreshToken);
        return response;
    }

    private String createRefreshToken(UserDTO user) {
        String refreshToken = UUID.randomUUID().toString();
        refreshTokenRepository.save(new RefreshTokenEntity(
                refreshToken,
                LocalDateTime.now().plusDays(refreshTokenExpireTime).toInstant(clock.getZone().getRules().getOffset(Instant.now())),
                userService.findUserEntityById(user.getUserId())));
        return refreshToken;
    }

    private String createAccessToken(UserDTO user, Algorithm algorithm) {
        return JWT.create()
                .withSubject(user.getUserId().toString())
                .withExpiresAt(new Date(System.currentTimeMillis() + accessTokenExpireTime))
                .withIssuer("cinema-tickets-service")
                .withClaim("roles", user.getRoles().stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList()
                )
                .withClaim("email", user.getEmail())
                .withClaim("fullName", user.getName().concat(" ").concat(user.getSurname()))
                .sign(algorithm);
    }

    //TODO: add checking date
    private UserDTO verifyRefreshToken(String refreshToken) {
        RefreshTokenEntity refreshTokenEntity = refreshTokenRepository.findById(refreshToken).orElseThrow(
                () -> new ObjectNotExistsException("Cannot find refresh token for this user")
        );
        Instant now = Instant.now(clock);
        if (refreshTokenEntity.getDateExpire().isAfter(now)) {
            throw new TokenExpiredException("Token has been already expired");
        }
        return userMapper.fromEntityToDTO(refreshTokenEntity.getUser());
    }
}
