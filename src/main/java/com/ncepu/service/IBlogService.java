package com.ncepu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ncepu.common.result.PageResult;
import com.ncepu.dto.BlogQueryParams;
import com.ncepu.entity.Blog;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    PageResult<Blog> getAllBlogs(int pageNumber, int pageSize);

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
    List<Blog> getBlogsByCategory(String category, int pageSize, int currentPage, boolean isSortDesc);

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
    List<Blog> getBlogsByCollection(String collection, int pageSize, int currentPage, boolean isSortByTimeDesc, boolean isSortDesc);

    /**
     * 获取指定合集的数量
     */
    long getCountByCollection(String collection);

    /**
     * 简要查询博客信息(不含content)
     */
    List<Blog> getShortBlogsList(int currentPage, int pageSize);

    /**
     * 更新博客
     */
    long updateBlog(Blog blog);

    /**
     * 上传新博客
     */
    long uploadBlog(Blog blog);

    /**
     * 获取网站最后一次更新时间
     */
    String getLastUpdateTime();

    /**
     * 搜索
     */
    List<Blog> search(String keywords);

    Object getBlogs(BlogQueryParams params);

    /**
     *  获取集合名称列表
     * @return
     */
    List<String> getCollectionsName();

    /**
     * 解析文章内容
     */
    Blog parseFileContent(MultipartFile textFile) throws IOException;

    /**
     * 通过名字检查博客在数据库中是否存在
     * @param title
     * @return
     */
    boolean checkBlogExistByTitle(String title);

    /**
     * 通过title对博客进行更新
     * @param blog
     * @return
     */
    boolean updateBlogByTitle(Blog blog);

    /**
     * 获取一个随机的图片地址
     * @return
     */
    String getRandomImgUrl();
}
