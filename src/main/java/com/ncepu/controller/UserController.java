package com.ncepu.controller;

import com.ncepu.entity.User;
import com.ncepu.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.print.Book;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     * 获取作者的信息
     * @return 作者信息
     */
    @GetMapping("/getAuthorInfo")
    public User getUserInfo(){
        return userService.getById(1);
    }
}
