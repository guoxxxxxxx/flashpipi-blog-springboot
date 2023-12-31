package com.ncepu.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.ncepu.entity.Information;
import com.ncepu.service.IInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    /**
     * 更新小站资讯
     */
    @GetMapping("/updateInfo")
    @SaCheckPermission("user:modify")
    public long updateInfo(@RequestParam String url, @RequestParam String notice){
        return service.updateInfo(url, notice);
    }
}
