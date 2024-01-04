package com.portfolio.main.application.menu.exception;

import com.portfolio.main.common.exception.BusiException;
import org.springframework.http.HttpStatus;

public class InvalidCreateMenuException extends BusiException {
    public static final String MESSAGE = "입력 값을 확인해주세요.";
    public InvalidCreateMenuException() {
        super(MESSAGE);
    }

    public InvalidCreateMenuException(String fieldName, String message) {
        super(MESSAGE);
        addValidation(fieldName, message);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }
}
