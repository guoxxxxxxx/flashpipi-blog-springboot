package com.ncepu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ncepu.entity.Information;

public interface IInformationService extends IService<Information> {

    /**
     * 访客数量+1
     */
    void addViews();

    /**
     * 更新小站资讯
     */
    long updateInfo(String url, String notice);
}
