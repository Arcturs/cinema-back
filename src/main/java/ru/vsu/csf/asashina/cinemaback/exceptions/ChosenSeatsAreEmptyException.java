package ru.vsu.csf.asashina.cinemaback.exceptions;

public class ChosenSeatsAreEmptyException extends BaseException{

    public ChosenSeatsAreEmptyException() {
        super();
    }

    public ChosenSeatsAreEmptyException(String message) {
        super(message);
    }
}
