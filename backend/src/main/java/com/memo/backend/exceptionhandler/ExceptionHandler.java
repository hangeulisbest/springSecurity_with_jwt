package com.memo.backend.exceptionhandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
/**
 * ExceptionHandler 설명 : ExceptionHandler에서 BizException 하나만 가지고 여러개의 ExceptionType으로 정의하여 예외반환
 * @author jowonjun
 * @version 1.0.0
 * 작성일 : 2022/01/18
**/
@Slf4j
@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<ErrorResult> bizException(BizException e){
        return new ResponseEntity<>(ErrorResult.create(e.getBaseExceptionType()), HttpStatus.BAD_REQUEST);
    }

}
