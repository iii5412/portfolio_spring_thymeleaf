package com.portfolio.main.presentation.rest.menu.exception;

import com.portfolio.main.common.exception.FieldValidationException;
import org.springframework.http.HttpStatus;

public class InvalidMenuInputException extends FieldValidationException {
    public static final String MESSAGE = "입력 값을 확인해주세요.";

    public InvalidMenuInputException() {
        super(MESSAGE);
    }

    public InvalidMenuInputException(String fieldName, String message) {
        super(MESSAGE);
        addValidation(fieldName, message);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }
}
