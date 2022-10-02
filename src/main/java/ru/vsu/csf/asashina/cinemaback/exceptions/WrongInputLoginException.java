package ru.vsu.csf.asashina.cinemaback.exceptions;

public class WrongInputLoginException extends BaseException{
    public WrongInputLoginException() {
        super();
    }

    public WrongInputLoginException(String message) {
        super(message);
    }
}
