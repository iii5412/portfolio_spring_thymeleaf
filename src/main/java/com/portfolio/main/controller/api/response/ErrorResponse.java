package com.portfolio.main.controller.api.response;

import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ErrorResponse {
    private final String code;
    private final String message;
    private final Map<String, String> validation;

    @Builder
    public ErrorResponse(String code, String message, Map<String, String> validation) {
        this.code = code;
        this.message = message;

        if (validation == null)
            this.validation = new HashMap<>();
        else
            this.validation = validation;
    }

    public void addValidation(String filedName, String errorMessage) {
        this.validation.put(filedName, errorMessage);
    }
}
