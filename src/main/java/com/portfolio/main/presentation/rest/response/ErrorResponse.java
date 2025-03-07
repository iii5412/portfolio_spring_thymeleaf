package com.portfolio.main.presentation.rest.response;

import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public record ErrorResponse(String code, String errorName, String message, Map<String, String> validation, Boolean isFieldException) {
    @Builder
    public ErrorResponse(String code, String errorName, String message, Map<String, String> validation, Boolean isFieldException) {
        this.code = code;
        this.errorName = errorName;
        this.message = message;

        this.isFieldException = Objects.requireNonNullElse(isFieldException, false);

        this.validation = Objects.requireNonNullElseGet(validation, HashMap::new);
    }

    public void addValidation(String filedName, String errorMessage) {
        this.validation.put(filedName, errorMessage);
    }
}
