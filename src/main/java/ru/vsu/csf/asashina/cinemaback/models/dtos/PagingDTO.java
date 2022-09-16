package ru.vsu.csf.asashina.cinemaback.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PagingDTO {

    private Integer pageNumber;
    private Integer pageSize;
    private Integer totalPages;
}
