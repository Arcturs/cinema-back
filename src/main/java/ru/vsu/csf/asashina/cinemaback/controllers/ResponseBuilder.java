package ru.vsu.csf.asashina.cinemaback.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.vsu.csf.asashina.cinemaback.models.dtos.PagingDTO;

import java.util.List;
import java.util.Map;

public class ResponseBuilder {

    public static ResponseEntity<?> build(HttpStatus httpStatus, Map<String, Object> data) {
        return ResponseEntity.status(httpStatus).body(data);
    }

    public static ResponseEntity<?> build(HttpStatus httpStatus, Object data) {
        return ResponseEntity.status(httpStatus).body(data);
    }

    public static ResponseEntity<?> build(PagingDTO pagingDto, List<?> data) {
        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "paging", pagingDto,
                "data", data
        ));
    }
}
