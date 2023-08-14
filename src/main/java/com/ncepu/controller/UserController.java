package com.ncepu.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.ncepu.VO.UserVO;
import com.ncepu.entity.User;
import com.ncepu.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     * 获取用户信息
     */
    @GetMapping("/getUserInfo")
    public User getUserInfo(@RequestParam String email){
        return userService.getUserInfo(email);
    }

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public long register(@RequestParam String email, @RequestParam String password, @RequestParam String code){
        return userService.register(email, password, code);
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public UserVO login(@RequestParam String email, @RequestParam String password){
        long login = userService.login(email, password);
        if (login == 1){
            User user = userService.getUserInfo(email);
            StpUtil.login(user.getId());
        }
        UserVO userVO = new UserVO();
        userVO.setStatus(login);
        userVO.setToken(StpUtil.getTokenInfo());
        return userVO;
    }

    /**
     * 忘记密码
     */
    @PostMapping("/modifyPassword")
    public long modifyPassword(@RequestParam String email, @RequestParam String newPassword, @RequestParam String code){
        return userService.modifyPassword(email, newPassword, code);
    }

    /**
     * 修改用户信息
     */
    @PostMapping("/modifyInfo")
    @SaCheckLogin()
    public long modifyInfo(@RequestBody User user){
        return userService.modifyInfo(user);
    }

    /**
     * 退出
     */
    @SaCheckLogin()
    @GetMapping("exit")
    public long exit(){
        StpUtil.logout();
        return 1;
    }
}
