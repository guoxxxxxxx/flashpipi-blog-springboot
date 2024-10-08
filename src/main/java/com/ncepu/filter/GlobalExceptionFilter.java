/**
 * @Time: 2024/8/16 15:21
 * @Author: guoxun
 * @File: GlobalExceptionFilter
 * @Description:
 */

package com.ncepu.filter;

import com.ncepu.common.result.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionFilter{

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleException(Exception e){
        log.error(e.toString());
        Map<String, Object> response = new HashMap<>();
        response.put("code", 500);
        response.put("message", e.toString());
        response.put("status", false);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UndeclaredThrowableException.class)
    public ResponseEntity<Object>  handleUndeclaredThrowableException(UndeclaredThrowableException e){
        log.error(e.getCause().toString());
        Map<String, Object> response = new HashMap<>();
        response.put("code", 500);
        response.put("message", e.getCause().toString());
        response.put("status", false);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}