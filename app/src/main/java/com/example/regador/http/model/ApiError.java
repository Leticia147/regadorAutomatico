package com.example.regador.http.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiError {
    @JsonProperty(value="message")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ApiError(String message) {
        this.message = message;
    }
}