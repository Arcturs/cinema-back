package ru.vsu.csf.asashina.cinemaback.exceptions;

public class SessionHasAlreadyStartedException extends BaseException{

    public SessionHasAlreadyStartedException() {
    }

    public SessionHasAlreadyStartedException(String message) {
        super(message);
    }
}
