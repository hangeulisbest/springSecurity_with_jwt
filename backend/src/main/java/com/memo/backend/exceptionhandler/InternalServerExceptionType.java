package com.memo.backend.exceptionhandler;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum InternalServerExceptionType implements BaseExceptionType{
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR","서버 내부에 문제가 발생하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String errorCode;
    private final String message;
    private final HttpStatus httpStatus;

    InternalServerExceptionType(String errorCode, String message, HttpStatus httpStatus) {
        this.errorCode = errorCode;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    @Override
    public String getErrorCode() {
        return null;
    }

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return null;
    }
}
