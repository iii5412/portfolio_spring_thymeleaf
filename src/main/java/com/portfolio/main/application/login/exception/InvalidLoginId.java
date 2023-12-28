package com.portfolio.main.application.login.exception;

import com.portfolio.main.common.exception.BusiException;
import org.springframework.http.HttpStatus;

public class InvalidLoginId extends BusiException {
    public static final String MESSAGE = "아이디를 확인해주세요.";

    public InvalidLoginId() {
        super(MESSAGE);
    }

    public InvalidLoginId(String fieldName, String message) {
        super(MESSAGE);
        addValidation(fieldName, message);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

}
