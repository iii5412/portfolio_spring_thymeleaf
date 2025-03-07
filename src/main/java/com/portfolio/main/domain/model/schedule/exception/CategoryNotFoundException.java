package com.portfolio.main.domain.model.schedule.exception;

import com.portfolio.main.common.exception.CustomException;
import org.springframework.http.HttpStatus;

public class CategoryNotFoundException extends CustomException {
    public static final String MESSAGE = "Category를 찾을 수 없습니다.";

    public CategoryNotFoundException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }
}
