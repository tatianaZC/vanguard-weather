package com.vanguard.weather.exceptions;

public class LimitRequestException extends RuntimeException {

    public LimitRequestException(String message) {
        super(message);
    }

    public LimitRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
