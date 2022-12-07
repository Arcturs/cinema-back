package ru.vsu.csf.asashina.cinemaback.exceptions;

public class TicketDateExpireException extends BaseException{

    public TicketDateExpireException() {
        super();
    }

    public TicketDateExpireException(String message) {
        super(message);
    }
}
