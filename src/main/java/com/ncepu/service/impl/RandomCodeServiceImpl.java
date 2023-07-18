package com.ncepu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ncepu.dao.RandomCodeDao;
import com.ncepu.dao.UserDao;
import com.ncepu.entity.RandomCode;
import com.ncepu.entity.User;
import com.ncepu.service.IRandomCodeService;
import com.ncepu.utils.mail.RandomCodeUtils;
import com.ncepu.utils.mail.SendRandomCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RandomCodeServiceImpl extends ServiceImpl<RandomCodeDao, RandomCode> implements IRandomCodeService {

    @Autowired
    private RandomCodeDao randomCodeDao;
    @Autowired
    private UserDao userDao;

    @Override
    public int getCode(String email, int model) {
        RandomCode code = new RandomCode();
        code.setEmail(email);
        code.setCode(RandomCodeUtils.getRandomCode());
        boolean emailIsExist = userDao.selectCount(new QueryWrapper<User>().eq("email", email)) == 1;
        int changeRow = 0;
        if(model==2){
            if (emailIsExist){
                return -2;  // 该邮箱已经被注册
            }
            else {
                // 此处是判断是要更新数据库中的信息还是新插入信息
                if(randomCodeDao.selectCount(new QueryWrapper<RandomCode>().eq("email", email)) == 1){
                    changeRow =  randomCodeDao.update(code ,new QueryWrapper<RandomCode>().eq("email", email));
                }
                else {
                    changeRow = randomCodeDao.insert(code);
                }
                // 发送邮件
                SendRandomCode.sendMessage(code.getCode(), email);
                return changeRow;   //验证码发送成功
            }
        }
        else if(model == 3){
            if (emailIsExist){
                // 此处是判断是要更新数据库中的信息还是新插入信息
                if(randomCodeDao.selectCount(new QueryWrapper<RandomCode>().eq("email", email)) == 1){
                    changeRow =  randomCodeDao.update(code ,new QueryWrapper<RandomCode>().eq("email", email));
                }
                else {
                    changeRow = randomCodeDao.insert(code);
                }
                // 发送邮件
                SendRandomCode.sendMessage(code.getCode(), email);
                return changeRow;   //验证码发送成功
            }
            else {
                return -3;  // 改邮箱尚未注册
            }
        }
        return changeRow;
    }

    @Override
    public RandomCode getCodeByEmail(String email) {
        return randomCodeDao.selectOne(new QueryWrapper<RandomCode>().eq("email", email));
    }
}
