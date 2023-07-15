package com.ncepu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ncepu.entity.Blog;
import org.springframework.web.bind.annotation.RequestParam;

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

    /**
     * 分类查询博客
     */
    List<Blog> getBlogsByCategory(String category, int pageSize, int currentPage);

    /**
     * 分类查询博客的数量
     */
    long getBlogsCountByCategory(String category);

    /**
     * 查询合集数量
     */
    List<Map<String, Object>> getCollectionCount();

    /**
     * 分合集查询博客
     */
    List<Blog> getBlogsByCollection(String collection, int pageSize, int currentPage);

    /**
     * 获取指定合集的数量
     */
    long getCountByCollection(String collection);

    /**
     * 简要查询博客信息(不含content)
     */
    List<Blog> getShortBlogsList(int currentPage, int pageSize);
}
