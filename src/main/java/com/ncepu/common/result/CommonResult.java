/**
 * @Time: 2024/8/16 9:17
 * @Author: guoxun
 * @File: CommonResult
 * @Description:
 */

package com.ncepu.common.result;

import lombok.Data;

@Data
public class CommonResult<T> {
    private T data;
    private Integer status;
    private String message;

    public CommonResult<T> data(T data){
        this.data = data;
        return this;
    }

    public CommonResult<T> message(String message){
        this.message = message;
        return this;
    }

    public CommonResult<T> success(){
        this.status = 200;
        return this;
    }

    public CommonResult<T> fail(){
        this.status = 500;
        return this;
    }

    public CommonResult<T> setStatusCode(Integer statusCode){
        this.status = statusCode;
        return this;
    }
}
