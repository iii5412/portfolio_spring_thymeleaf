package com.portfolio.main.domain.model.menu.exception;

import com.portfolio.main.common.exception.BusiException;
import org.springframework.http.HttpStatus;

public class MenuCannotBeOwnParentException extends BusiException {
    public static final String MESSAGE = "자신의 ID를 상위 메뉴로 설정할 수 없습니다.";

    public MenuCannotBeOwnParentException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }
}
