package com.portfolio.main.application.program.exception;

import com.portfolio.main.common.exception.BusiException;
import org.springframework.http.HttpStatus;

public class DuplicatedProgramUrlException extends BusiException {
    public static final String MESSAGE = "프로그램의 URL이 중복됩니다.";
    public DuplicatedProgramUrlException() {
        super(MESSAGE);
        addValidation("url", MESSAGE);
    }

    public DuplicatedProgramUrlException(String fieldName, String message) {
        super(MESSAGE);
        addValidation(fieldName, message);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.CONFLICT.value();
    }
}
