package ru.vsu.csf.asashina.cinemaback.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.vsu.csf.asashina.cinemaback.models.dtos.UserDTO;
import ru.vsu.csf.asashina.cinemaback.models.request.LoginForm;
import ru.vsu.csf.asashina.cinemaback.models.request.SignUpForm;
import ru.vsu.csf.asashina.cinemaback.services.TokenService;
import ru.vsu.csf.asashina.cinemaback.services.UserService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.security.Principal;
import java.util.Map;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final TokenService tokenService;

    @GetMapping("/credentials")
    public ResponseEntity<?> getUser(Authentication authentication) {
        String email = (String) authentication.getPrincipal();
        UserDTO userDto = userService.findUserByEmail(email);
        return ResponseBuilder.build(OK, userDto);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> register(@Valid @RequestBody SignUpForm signUpForm) {
        UserDTO user = userService.registerNewUser(signUpForm);
        return ResponseBuilder.build(CREATED, Map.of(
                "tokens", tokenService.createTokens(user)
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginForm loginForm) {
        UserDTO user = userService.login(loginForm);
        return ResponseBuilder.build(OK, Map.of(
                "tokens", tokenService.createTokens(user)
        ));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestParam("refreshToken") @NotBlank String refreshToken) {
        return ResponseBuilder.build(OK, tokenService.refreshToken(refreshToken));
    }
}
