package ru.vsu.csf.asashina.cinemaback.exceptions;

public class SeatsAreAlreadyBookedException extends BaseException{

    public SeatsAreAlreadyBookedException() {
    }

    public SeatsAreAlreadyBookedException(String message) {
        super(message);
    }
}
