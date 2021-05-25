package com.vadzimvincho.exceptions;

public class DaoException extends Exception {
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
