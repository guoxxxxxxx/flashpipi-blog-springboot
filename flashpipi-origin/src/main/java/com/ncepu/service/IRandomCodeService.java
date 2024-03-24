package com.ncepu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ncepu.entity.RandomCode;
import org.springframework.stereotype.Service;


public interface IRandomCodeService extends IService<RandomCode> {

    /**
     * 获取验证码
     */
    int getCode(String email, int model);

    /**
     * 根据邮箱从数据库中查找验证码
     */
    RandomCode getCodeByEmail(String email);
}
