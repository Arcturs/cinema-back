package ru.vsu.csf.asashina.cinemaback.models.enumerations;

public enum MovieSortEnum {

    BY_TITLE("BY_TITLE"),
    BY_RATING("BY_RATING"),
    BY_DURATION("BY_DURATION");

    private String sort;

    MovieSortEnum(String sort) {
        this.sort = sort;
    }
}
