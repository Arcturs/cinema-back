package ru.vsu.csf.asashina.cinemaback.controllers;

import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.vsu.csf.asashina.cinemaback.exceptions.*;
import ru.vsu.csf.asashina.cinemaback.models.dtos.ErrorDTO;

import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
@Slf4j
public class ErrorHandler {

    private static final String INTERNAL_SERVER_ERROR_TEXT = "Internal server error";

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> internalServerErrorHandler(Exception e) {
        log.error(e.getMessage());
        return ResponseBuilder.build(INTERNAL_SERVER_ERROR, new ErrorDTO(INTERNAL_SERVER_ERROR_TEXT));
    }

    @ExceptionHandler(ObjectNotExistsException.class)
    public ResponseEntity<?> notFoundHandler(BaseException e) {
        return ResponseBuilder.build(NOT_FOUND, new ErrorDTO(e.getMessage()));
    }

    @ExceptionHandler({PosterException.class, TypeMismatchException.class, PasswordDoesNotMatchException.class,
            WrongInputLoginException.class, ChosenSeatsAreEmptyException.class})
    public ResponseEntity<?> badRequestHandler(Exception e) {
        return ResponseBuilder.build(BAD_REQUEST, new ErrorDTO((e.getMessage())));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public ResponseEntity<?> methodArgumentNotValidExceptionHandler(BindException e) {
        return ResponseBuilder.build(BAD_REQUEST,
                e.getBindingResult().getAllErrors().stream()
                        .map(error -> (FieldError) error)
                        .collect(Collectors.toMap(
                                FieldError::getField,
                                DefaultMessageSourceResolvable::getDefaultMessage,
                                (message1, message2) -> message1 + ", " + message2
                        )));
    }

    @ExceptionHandler({SessionsExistException.class, SessionDateTimeException.class, UserAlreadyExistsException.class,
            SeatsAreAlreadyBookedException.class})
    public ResponseEntity<?> conflictErrorHandler(BaseException e) {
        return ResponseBuilder.build(CONFLICT, new ErrorDTO(e.getMessage()));
    }

    @ExceptionHandler({MaxScreenNumberException.class, SessionHasAlreadyStartedException.class})
    public ResponseEntity<?> methodNotAllowedErrorHandler(BaseException e) {
        return ResponseBuilder.build(METHOD_NOT_ALLOWED, new ErrorDTO(e.getMessage()));
    }

    @ExceptionHandler({TokenExpiredException.class, TicketDoesNotBelongToUserException.class})
    public ResponseEntity<?> forbiddenErrorHandler(BaseException e) {
        return ResponseBuilder.build(FORBIDDEN, new ErrorDTO(e.getMessage()));
    }
}
