package com.portfolio.main.menu.exception;

import com.portfolio.main.exception.BusiException;
import org.springframework.http.HttpStatus;

public class CannotDeleteMenuWithSubmenusException extends BusiException {
    public static final String MESSAGE = "하위 메뉴가 존재하는 메뉴는 삭제할 수 없습니다.";

    public CannotDeleteMenuWithSubmenusException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }
}
