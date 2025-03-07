package com.portfolio.main.common.exception;

import lombok.Getter;

@Getter
public abstract class CustomException extends RuntimeException {
    private final String errorName;

    public CustomException(String message) {
        super(message);
        this.errorName = this.getClass().getSimpleName();
    }

    public CustomException(String message, Throwable cause) {
        super(message, cause);
        this.errorName = this.getClass().getName();
    }

    public abstract int getStatusCode();
}
