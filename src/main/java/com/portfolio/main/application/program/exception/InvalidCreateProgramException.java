package com.portfolio.main.application.program.exception;

import com.portfolio.main.common.exception.BusiException;
import org.springframework.http.HttpStatus;

public class InvalidCreateProgramException extends BusiException {
    public static final String MESSAGE = "입력 값을 확인해주세요.";
    public InvalidCreateProgramException() {
        super(MESSAGE);
    }

    public InvalidCreateProgramException(String fieldName, String message) {
        super(MESSAGE);
        addValidation(fieldName, message);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }
}
