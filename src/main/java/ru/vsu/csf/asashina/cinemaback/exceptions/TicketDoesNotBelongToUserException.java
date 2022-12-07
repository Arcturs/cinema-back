package ru.vsu.csf.asashina.cinemaback.exceptions;

public class TicketDoesNotBelongToUserException extends BaseException{

    public TicketDoesNotBelongToUserException() {
        super();
    }

    public TicketDoesNotBelongToUserException(String message) {
        super(message);
    }
}
