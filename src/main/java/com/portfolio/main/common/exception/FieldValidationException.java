package com.portfolio.main.common.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class FieldValidationException extends CustomException {
    private final Map<String, String> validation = new HashMap<>();

    public FieldValidationException(String message) {
        super(message);
    }

    public FieldValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public void addValidation(String fieldName, String message) {
        validation.put(fieldName, message);
    }

    public boolean hasErrors() {
        return !this.getValidation().isEmpty();
    }


}
