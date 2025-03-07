package com.portfolio.main.presentation.rest.account.login.exception;

import com.portfolio.main.common.exception.FieldValidationException;
import org.springframework.http.HttpStatus;

public class InvalidRegistUser extends FieldValidationException {
    public static final String MESSAGE = "회원가입의 유효성에 맞지 않는 데이터가 있습니다.";

    public InvalidRegistUser() {
        super(MESSAGE);
    }

    public InvalidRegistUser(String fieldName, String message) {
        super(MESSAGE);
        addValidation(fieldName, message);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }
}
