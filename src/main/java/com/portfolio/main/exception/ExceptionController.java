package com.portfolio.main.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(BusiException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> busiException(BusiException e) {
        final int statusCode = e.getStatusCode();
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .code(String.valueOf(statusCode))
                .message(e.getMessage())
                .validation(e.getValidation())
                .build();

        return ResponseEntity.status(statusCode).body(errorResponse);
    }
}
