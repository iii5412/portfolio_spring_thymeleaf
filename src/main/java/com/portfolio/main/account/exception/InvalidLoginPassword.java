package com.portfolio.main.account.exception;

import com.portfolio.main.exception.BusiException;
import org.springframework.http.HttpStatus;

public class InvalidLoginPassword extends BusiException {
    public static final String MESSAGE = "비밀번호를 확인해주세요.";

    public InvalidLoginPassword() {
        super(MESSAGE);
    }

    public InvalidLoginPassword(String fieldName, String message) {
        super(MESSAGE);
        addValidation(fieldName, message);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }
}
