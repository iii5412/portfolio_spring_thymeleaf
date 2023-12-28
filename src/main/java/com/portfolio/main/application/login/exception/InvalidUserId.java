package com.portfolio.main.application.login.exception;

import com.portfolio.main.common.exception.BusiException;
import org.springframework.http.HttpStatus;

public class InvalidUserId extends BusiException {
    public static final String MESSAGE = "아이디를 확인해주세요.";

    public InvalidUserId() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

}
