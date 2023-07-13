package com.ncepu.controller;

import com.ncepu.entity.Information;
import com.ncepu.service.IInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/info")
public class InformationController {
    @Autowired
    private IInformationService service;

    /**
     * 获取小站资讯信息
     */
    @GetMapping("/getInfo")
    public Information getInfo(){
        // 每次访问主页时默认访客+1
        service.addViews();
        return service.getById(1);
    }
}
