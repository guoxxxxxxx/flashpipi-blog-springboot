package com.ncepu.token;

import cn.dev33.satoken.stp.StpInterface;
import com.ncepu.entity.User;
import com.ncepu.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StpInterfaceImpl implements StpInterface {

    @Autowired
    IUserService userService;

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        List<String> list = new ArrayList<>();
        User user = userService.getUserInfoById(Integer.valueOf((String) loginId));
        list.add("user:normal");  // 所有用户都有访问权限
        if (user.getRankLevel() == 1){
            list.add("user:see");   // 用户可以访问管理员界面查看权限
        }
        else if (user.getRankLevel() == 2){
            list.add("user:modify");    // 修改权限
            list.add("user:see");
        }
        return list;
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        List<String> list = new ArrayList<>();
        User user = userService.getUserInfoById(Integer.valueOf((String) loginId));
        if (user.getId() == 2){
            list.add("super-admin");
        }
        else {
            list.add("user");
        }
        return list;
    }
}
