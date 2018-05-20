package com.wiley.firewatch.server.exceptions;

public class FirewatchServerException extends RuntimeException {
    public FirewatchServerException() {
    }

    public FirewatchServerException(String message) {
        super(message);
    }

    public FirewatchServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public FirewatchServerException(Throwable cause) {
        super(cause);
    }

    public FirewatchServerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
