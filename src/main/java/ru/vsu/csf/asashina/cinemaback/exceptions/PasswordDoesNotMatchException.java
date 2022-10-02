package ru.vsu.csf.asashina.cinemaback.exceptions;

public class PasswordDoesNotMatchException extends BaseException{
    public PasswordDoesNotMatchException() {
        super();
    }

    public PasswordDoesNotMatchException(String message) {
        super(message);
    }
}
