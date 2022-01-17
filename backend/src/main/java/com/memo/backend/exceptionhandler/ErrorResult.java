package com.memo.backend.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
/**
 * ErrorResult 설명 : 반환할 에러의 기본형태
 * @author jowonjun
 * @version 1.0.0
 * 작성일 : 2022/01/18
**/
@Data
@AllArgsConstructor
public class ErrorResult {
    private String code;
    private String message;

    static ErrorResult create(BaseExceptionType baseExceptionType){
        return new ErrorResult(baseExceptionType.getErrorCode(),baseExceptionType.getMessage());
    }
}
