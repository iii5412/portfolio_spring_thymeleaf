package com.portfolio.main.application.login.exception;

import com.portfolio.main.common.exception.BusiException;
import org.springframework.http.HttpStatus;

public class InvalidRegistUser extends BusiException {
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
