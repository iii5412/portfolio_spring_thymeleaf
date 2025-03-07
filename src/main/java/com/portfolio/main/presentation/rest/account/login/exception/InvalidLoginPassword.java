package com.portfolio.main.presentation.rest.account.login.exception;

import com.portfolio.main.common.exception.CustomException;
import com.portfolio.main.common.exception.FieldValidationException;
import org.springframework.http.HttpStatus;

public class InvalidLoginPassword extends FieldValidationException {
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
