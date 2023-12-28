package com.portfolio.main.domain.model.menu.exception;

import com.portfolio.main.common.exception.BusiException;
import org.springframework.http.HttpStatus;

public class MenuNotFoundException extends BusiException {
    public static final String MESSAGE = "메뉴를 찾을 수 없습니다.";

    public MenuNotFoundException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }
}
