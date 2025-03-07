package com.portfolio.main.presentation.rest.menu.exception;

import com.portfolio.main.common.exception.CustomException;
import org.springframework.http.HttpStatus;

public class MissingMenuIdException  extends CustomException {
    public static final String MESSAGE = "Menu ID가 존재하지 않습니다.";
    public MissingMenuIdException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }
}
