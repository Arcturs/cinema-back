package ru.vsu.csf.asashina.cinemaback.exceptions;

public class OrderIsAlreadyPaidException extends BaseException{

    public OrderIsAlreadyPaidException() {
        super();
    }

    public OrderIsAlreadyPaidException(String message) {
        super(message);
    }
}
