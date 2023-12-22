package com.portfolio.main.menu.exception;

import com.portfolio.main.exception.BusiException;
import org.springframework.http.HttpStatus;

public class NotFoundMenuRoleException extends BusiException {
    public static final String MESSAGE = "MenuRole을 찾을 수 없습니다.";

    public NotFoundMenuRoleException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }
}
