package com.ncepu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ncepu.entity.User;

public interface IUserService extends IService<User> {

    /**
     * 注册
     */
    long register(String email, String password, String code);

    /**
     * 登录
     */
    long login(String email, String password);

    /**
     * 忘记密码
     */
    long modifyPassword(String email, String newPassword, String code);

    /**
     * 获取用户信息-通过email
     */
    User getUserInfo(String email);

    /**
     * 修改用户信息
     */
    long modifyInfo(User user);
}
