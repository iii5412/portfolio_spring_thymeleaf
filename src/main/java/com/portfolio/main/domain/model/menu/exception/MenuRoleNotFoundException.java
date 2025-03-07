package com.portfolio.main.domain.model.menu.exception;

import com.portfolio.main.common.exception.CustomException;
import org.springframework.http.HttpStatus;

public class MenuRoleNotFoundException extends CustomException {
    public static final String MESSAGE = "MenuRole을 찾을 수 없습니다.";

    public MenuRoleNotFoundException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }
}
