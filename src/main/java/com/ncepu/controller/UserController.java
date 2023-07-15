package com.ncepu.controller;

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
    public long login(@RequestParam String email, @RequestParam String password){
        return userService.login(email, password);
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
    public long modifyInfo(@RequestBody User user){
        return userService.modifyInfo(user);
    }
}
