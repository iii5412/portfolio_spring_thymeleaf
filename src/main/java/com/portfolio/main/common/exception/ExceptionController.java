package com.portfolio.main.common.exception;

import com.portfolio.main.presentation.rest.response.ErrorResponse;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.nio.file.AccessDeniedException;
import java.util.Map;

@ControllerAdvice
public class ExceptionController {
    /**
     * TODO ::
     * 405 METHOD_NOT_ALLOWED 일때 처리 어떻게?
     * 비동기인지 아닌지에 대한 구분header를 사용해야할까
     * 아니면 이미 구분해주는 header가 있을까?
     * ResponseEntity는 비동기 요청일때만 사용해야한다.
     */

    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> customException(CustomException e) {
        final int statusCode = e.getStatusCode();
        final ErrorResponse errorResponse = buildErrorResponse(statusCode, e.getMessage(), e.getErrorName());

        return ResponseEntity.status(statusCode).body(errorResponse);
    }

    @ExceptionHandler(FieldValidationException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> fieldValidationException(FieldValidationException e) {
        final int statusCode = e.getStatusCode();
        final ErrorResponse errorResponse = buildFiledErrorResponse(statusCode, e.getMessage(), e.getErrorName(), e.getValidation());

        return ResponseEntity.status(statusCode).body(errorResponse);
    }

    @ExceptionHandler(DataAccessException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> dataAccessException(DataAccessException e) {
        final int statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
        final ErrorResponse errorResponse = buildErrorResponse(statusCode, "dataAccessException", "데이터를 가져오는데 문제가 발생했습니다.");

        return ResponseEntity.status(statusCode).body(errorResponse);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> accessDeniedException(AccessDeniedException e) {
        final int statusCode = HttpStatus.FORBIDDEN.value();
        final ErrorResponse errorResponse = buildErrorResponse(statusCode,"accessDeniedException",  "접근 권한이 없습니다.");

        return ResponseEntity.status(statusCode).body(errorResponse);
    }

    private ErrorResponse buildErrorResponse(final int statusCode, final String message, final String errorName) {
        return buildErrorResponse(statusCode, message, errorName, null);
    }

    private ErrorResponse buildErrorResponse(int statusCode, String message, String errorName, Map<String, String> validation) {
        return ErrorResponse.builder()
                .code(String.valueOf(statusCode))
                .message(message)
                .errorName(errorName)
                .validation(validation)
                .build();
    }

    private ErrorResponse buildFiledErrorResponse(int statusCode, String message, String errorName, Map<String, String> validation) {
        return ErrorResponse.builder()
                .code(String.valueOf(statusCode))
                .message(message)
                .errorName(errorName)
                .validation(validation)
                .isFieldException(true)
                .build();
    }

}
