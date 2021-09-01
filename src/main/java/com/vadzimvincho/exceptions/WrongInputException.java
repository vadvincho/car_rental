package com.vadzimvincho.exceptions;

public class WrongInputException extends Exception {
    public WrongInputException(Exception e) {
        super(e);
    }

    public WrongInputException(String message, Exception e) {
        super(message, e);
    }

    public WrongInputException(String message) {
        super(message);
    }
}
