package com.portfolio.main.domain.model.account.exception;

import com.portfolio.main.common.exception.BusiException;
import org.springframework.http.HttpStatus;

public class RoleNotFoundException extends BusiException {
    public static final String MESSAGE = "권한을 찾을 수 없습니다.";

    public RoleNotFoundException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }
}
