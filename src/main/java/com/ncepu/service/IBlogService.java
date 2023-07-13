package com.ncepu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ncepu.entity.Blog;

import java.util.List;
import java.util.Map;

public interface IBlogService extends IService<Blog> {
    /**
     * 查询博客种类数量
     */
    List<Map<String, Object>> getBlogCategoryList(int page);

    /**
     * 根据日期排序选取最近的五篇文章作为轮播图
     */
    List<Blog> getRecentBlogs(int page, int size);

    /**
     * 获取博客列表-分页获取
     */
    List<Blog> getAllBlogs(int page);

    /**
     * 查询博客种类数量
     */
    long getBlogsCategoryCount();

    /**
     * 将访问记录+1
     */
    void addViewsCount(int id);

    /**
     * 查询合集数
     */
    long getBlogCollectionCount();

    /**
     * 查询所有文章的访客数量
     */
    List<Map<String, Object>> getAllBlogsViewsCount();
}
