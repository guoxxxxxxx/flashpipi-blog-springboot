/**
 * @Time: 2024/8/16 15:21
 * @Author: guoxun
 * @File: GlobalExceptionFilter
 * @Description:
 */

package com.ncepu.common.filter;

import com.ncepu.common.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionFilter{

    @ExceptionHandler({Exception.class})
    public CommonResult<Object> handleException(Exception e){
        log.error(e.toString());
        return new CommonResult<>().fail().message(e.toString());
    }


}