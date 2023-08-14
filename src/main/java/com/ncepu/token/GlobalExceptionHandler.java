package com.ncepu.token;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import com.ncepu.VO.TokenVO;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public TokenVO handlerException(NotLoginException e){
        TokenVO tokenVO = new TokenVO();
        tokenVO.setStatus(444);
        return tokenVO;
    }

    @ExceptionHandler
    public TokenVO handlerPermissionException(NotPermissionException e){
        TokenVO tokenVO = new TokenVO();
        tokenVO.setStatus(443);
        return tokenVO;
    }
}
