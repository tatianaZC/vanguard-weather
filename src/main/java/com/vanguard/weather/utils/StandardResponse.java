package com.vanguard.weather.utils;

public class StandardResponse<T> {

    private T response;

    public StandardResponse(T response) {
        this.response = response;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }
}
