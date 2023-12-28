package com.portfolio.main.domain.model.menu.exception;

import com.portfolio.main.common.exception.BusiException;
import org.springframework.http.HttpStatus;

public class ProgramNotFoundException extends BusiException {
    public static final String MESSAGE = "프로그램을 찾을 수 없습니다.";

    public ProgramNotFoundException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }
}
