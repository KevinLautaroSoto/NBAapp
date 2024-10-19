package com.lautaro.NbaApp.exceptions;

import org.springframework.dao.DataAccessException;

public class CustomDatabaseException extends RuntimeException{
    public CustomDatabaseException(String message) {
        super(message);
    }

    public CustomDatabaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomDatabaseException(String messagge, DataAccessException dataAccessException) {
        super(messagge, dataAccessException);
    }
}
