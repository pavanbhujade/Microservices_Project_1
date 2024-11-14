package com.hotelservice.exceptions;

public class NoDataFoundException extends RuntimeException {
    public NoDataFoundException(String s) {
        super(s);
    }

    public NoDataFoundException() {
        super("Resource not found exception !!!");
    }
}
