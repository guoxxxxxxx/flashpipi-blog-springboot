package com.ncepu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ncepu.entity.Blog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BlogDao extends BaseMapper<Blog> {
}
