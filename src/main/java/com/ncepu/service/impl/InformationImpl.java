package com.ncepu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ncepu.dao.InformationDao;
import com.ncepu.entity.Information;
import com.ncepu.service.IInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InformationImpl extends ServiceImpl<InformationDao, Information> implements IInformationService {

    @Autowired
    private InformationDao dao;

    @Override
    public void addViews() {
        Information information = dao.selectById(1);
        information.setViewsCount(information.getViewsCount()+1);
        dao.updateById(information);
    }
}
