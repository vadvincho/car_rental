package com.vadzimvincho.exceptions;

public class DaoException extends RuntimeException {
    public DaoException(Exception e) {
        super(e);
    }

    public DaoException(String message, Exception e) {
        super(message, e);
    }

    public DaoException(String message) {
        super(message);
    }
}
