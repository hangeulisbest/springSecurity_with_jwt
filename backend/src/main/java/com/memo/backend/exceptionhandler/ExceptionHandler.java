package com.memo.backend.exceptionhandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    /**
     * 개발자가 정의한 예외타입에 걸리는 경우는 아래의 메소드에서 예외가 던져짐
     * @author jowonjun
     * @version 1.0.0
     * 작성일 : 2022/01/18
    **/
    @org.springframework.web.bind.annotation.ExceptionHandler(BizException.class)
    public ResponseEntity<ErrorResult> bizException(BizException e){
        return new ResponseEntity<>(ErrorResult.create(e.getBaseExceptionType()), e.getBaseExceptionType().getHttpStatus());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResult> authenticationException(AuthenticationException e) {
        return new ResponseEntity<>(new ErrorResult("AUTH_ERROR",e.getMessage()),HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResult> accessDeniedException(AccessDeniedException e) {
        return new ResponseEntity<>(new ErrorResult("ACCESS_DENIED",e.getMessage()),HttpStatus.FORBIDDEN);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResult> notResolvedException(Exception e){
        return new ResponseEntity<>(ErrorResult.create(InternalServerExceptionType.INTERNAL_SERVER_ERROR),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * DTO에서 Validation에 실패할 경우 어떤 필드가 어떤 이유 때문에 validation에 실패하였는지 error message에 담아서 리턴한다.
     * @author jowonjun
     * @version 1.0.0
     * 작성일 : 2022/01/19
    **/
    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResult> dtoValidationException(MethodArgumentNotValidException e){
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder builder = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            builder.append("[")
                    .append(fieldError.getField())
                    .append("](은)는 ")
                    .append(fieldError.getDefaultMessage())
                    .append(" 입력된 값: [")
                    .append(fieldError.getRejectedValue())
                    .append("]");
        }
        return new ResponseEntity<>(new ErrorResult("DTO_VALIDATION_ERROR",builder.toString()),HttpStatus.BAD_REQUEST);
    }



}
