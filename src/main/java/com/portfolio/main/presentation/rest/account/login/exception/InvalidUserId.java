package com.portfolio.main.presentation.rest.account.login.exception;

import com.portfolio.main.common.exception.CustomException;
import org.springframework.http.HttpStatus;

public class InvalidUserId extends CustomException {
    public static final String MESSAGE = "아이디를 확인해주세요.";

    public InvalidUserId() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

}
