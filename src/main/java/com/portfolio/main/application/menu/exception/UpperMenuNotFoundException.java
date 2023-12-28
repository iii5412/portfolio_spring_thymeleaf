package com.portfolio.main.application.menu.exception;

import com.portfolio.main.common.exception.BusiException;
import org.springframework.http.HttpStatus;

public class UpperMenuNotFoundException extends BusiException {
    public static final String MESSAGE = "상위 메뉴를 찾을 수 없습니다.";

    public UpperMenuNotFoundException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }
}
