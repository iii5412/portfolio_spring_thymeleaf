package com.portfolio.main.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class BusiException extends RuntimeException {
    private final Map<String, String> validation = new HashMap<>();
    public BusiException(String message) {
        super(message);
    }

    public BusiException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract int getStatusCode();

    public void addValidation(String fieldName, String message) {
        validation.put(fieldName, message);
    }

    public boolean hasErrors() {
        return !this.getValidation().isEmpty();
    }
}
