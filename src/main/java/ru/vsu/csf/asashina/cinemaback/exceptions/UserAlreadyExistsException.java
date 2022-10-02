package ru.vsu.csf.asashina.cinemaback.exceptions;

public class UserAlreadyExistsException extends BaseException{
    public UserAlreadyExistsException() {
        super();
    }

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
