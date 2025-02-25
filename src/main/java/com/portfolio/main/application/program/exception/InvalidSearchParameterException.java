package com.portfolio.main.application.program.exception;

import com.portfolio.main.common.exception.BusiException;
import org.springframework.http.HttpStatus;

public class InvalidSearchParameterException  extends BusiException {
    public static final String MESSAGE = "검색어를 확인해주세요.";
    public InvalidSearchParameterException() {
        super(MESSAGE);
    }

    public InvalidSearchParameterException(String fieldName, String message) {
        super(MESSAGE);
        addValidation(fieldName, message);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }
}

