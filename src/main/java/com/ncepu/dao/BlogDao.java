package com.ncepu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ncepu.dto.BlogQueryParams;
import com.ncepu.entity.Blog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BlogDao extends BaseMapper<Blog> {

    List<Blog> queryByCondition(@Param("params") BlogQueryParams params);

    IPage<Blog> queryByCondition(IPage<Blog> page, @Param("params") BlogQueryParams params);
}
