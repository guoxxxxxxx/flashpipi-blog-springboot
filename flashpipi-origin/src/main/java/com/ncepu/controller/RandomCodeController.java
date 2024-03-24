package com.ncepu.controller;

import com.ncepu.entity.RandomCode;
import com.ncepu.service.IRandomCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/code")
public class RandomCodeController {

    @Autowired
    IRandomCodeService service;

    /**
     * 获取验证码
     */
    @GetMapping("/getCode")
    public int getCode(@RequestParam String email, @RequestParam int model){
        return service.getCode(email, model);
    }
}
