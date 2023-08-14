package com.ncepu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ncepu.dao.RandomCodeDao;
import com.ncepu.entity.RandomCode;
import com.ncepu.service.IRandomCodeService;
import com.ncepu.utils.MD5Utils;
import com.ncepu.dao.UserDao;
import com.ncepu.entity.User;
import com.ncepu.service.IUserService;
import com.ncepu.utils.mail.RandomCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements IUserService {
    @Autowired
    UserDao userDao;
    @Autowired
    IRandomCodeService randomCodeService;

    @Override
    public long register(String email, String password, String code) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        RandomCode codeByEmail = randomCodeService.getCodeByEmail(user.getEmail());
        if (!codeByEmail.getCode().equals(code)){
            // 返回-2说明验证码错误
            return -2;
        }
        else {
            // 删除记录
            randomCodeService.removeById(codeByEmail);
            // 对用户密码进行加密
            user.setPassword(MD5Utils.getMD5(user.getPassword()));
            // 检测该邮箱是否已经被注册
            if(userDao.selectCount(new QueryWrapper<User>().eq("email", user.getEmail())) == 1){
                // 返回-1说明该邮箱已被注册
                return -1;
            }
            else {
                return userDao.insert(user);
            }
        }
    }

    @Override
    public long login(String email, String password) {
        // 判断该邮箱是否已经被注册
        if(userDao.selectCount(new QueryWrapper<User>().eq("email", email)) == 1){
            // 已被注册情况
            User user = userDao.selectOne(new QueryWrapper<User>().eq("email", email));
            if (MD5Utils.getMD5(password).equals(user.getPassword())){
                // 返回1 说明登录成功
                return 1;
            }
            else {
                // 返回2 密码错误
                return 2;
            }
        }
        else {
            // 返回3 邮箱不存在
            return 3;
        }
    }

    @Override
    public long modifyPassword(String email, String newPassword, String code) {
        // 验证验证码是否正确
        RandomCode codeByEmail = randomCodeService.getCodeByEmail(email);
        if(codeByEmail!=null && codeByEmail.getCode().equals(code)){
            User user = new User();
            user.setPassword(MD5Utils.getMD5(newPassword));
            randomCodeService.remove(new QueryWrapper<RandomCode>().eq("email", email));
            return userDao.update(user, new QueryWrapper<User>().eq("email", email));
        }
        else {
            // 返回-1验证码错误
            return -1;
        }
    }

    @Override
    public User getUserInfo(String email) {
        return userDao.selectOne(new QueryWrapper<User>().eq("email", email));
    }

    @Override
    public long modifyInfo(User user) {
        return userDao.update(user, new QueryWrapper<User>().eq("email", user.getEmail()));
    }

    @Override
    public User getUserInfoById(Integer id) {
        return userDao.selectById(id);
    }
}
