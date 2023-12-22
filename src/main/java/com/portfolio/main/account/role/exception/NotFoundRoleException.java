package com.portfolio.main.account.role.exception;

import com.portfolio.main.exception.BusiException;
import org.springframework.http.HttpStatus;

public class NotFoundRoleException extends BusiException {
    public static final String MESSAGE = "권한을 찾을 수 없습니다.";

    public NotFoundRoleException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }
}
