package com.vanguard.weather.exceptions;

public class MissingApiKeyException extends RuntimeException {

    public MissingApiKeyException(String message) {
        super(message);
    }

    public MissingApiKeyException(String message, Throwable cause) {
        super(message, cause);
    }
}
