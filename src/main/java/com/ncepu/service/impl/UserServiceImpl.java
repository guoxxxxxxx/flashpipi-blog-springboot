package com.ncepu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ncepu.dao.UserDao;
import com.ncepu.entity.User;
import com.ncepu.service.IUserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements IUserService {

}
