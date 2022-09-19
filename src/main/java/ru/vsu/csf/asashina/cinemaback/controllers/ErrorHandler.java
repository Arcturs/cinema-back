package ru.vsu.csf.asashina.cinemaback.controllers;

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

import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
@Slf4j
public class ErrorHandler {

    private static final String INTERNAL_SERVER_ERROR_TEXT = "Internal server error";

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> internalServerErrorHandler(Exception e) {
        log.error(e.getMessage());
        return ResponseBuilder.build(INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR_TEXT);
    }

    @ExceptionHandler(ObjectNotExistsException.class)
    public ResponseEntity<?> notFoundHandler(BaseException e) {
        return ResponseBuilder.build(NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler({PosterException.class, TypeMismatchException.class})
    public ResponseEntity<?> badRequestHandler(Exception e) {
        return ResponseBuilder.build(BAD_REQUEST, e.getMessage());
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

    @ExceptionHandler(SessionsExistException.class)
    public ResponseEntity<?> conflictErrorHandler(BaseException e) {
        return ResponseBuilder.build(CONFLICT, e.getMessage());
    }

    @ExceptionHandler(MaxScreenNumberException.class)
    public ResponseEntity<?> methodNotAllowedErrorHandler(BaseException e) {
        return ResponseBuilder.build(METHOD_NOT_ALLOWED, e.getMessage());
    }
}
