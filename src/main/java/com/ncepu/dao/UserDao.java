package com.ncepu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ncepu.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao extends BaseMapper<User> {

}
