package com.ncepu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ncepu.dao.BlogDao;
import com.ncepu.entity.Blog;
import com.ncepu.service.IBlogService;
import com.ncepu.utils.DateUtils;
import com.ncepu.utils.SearchUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BlogServiceImpl extends ServiceImpl<BlogDao, Blog> implements IBlogService {
    @Autowired
    BlogDao blogDao;
    @Override
    public List<Map<String, Object>> getBlogCategoryList(int page) {
        if (page != -1){
            QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
            queryWrapper.select("category", "count(category) as count");
            queryWrapper.groupBy("category");
            IPage iPage = new Page(page, 4);
            blogDao.selectMapsPage(iPage, queryWrapper);
            return iPage.getRecords();
        }
        else {
            QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
            queryWrapper.select("category as name", "count(category) as value");
            queryWrapper.groupBy("category");
            return blogDao.selectMaps(queryWrapper);
        }
    }

    @Override
    public List<Blog> getRecentBlogs(int page, int size) {
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderBy(true,false, "update_time");
        IPage iPage = new Page(page, size);
        blogDao.selectPage(iPage, queryWrapper);
        return iPage.getRecords();
    }

    @Override
    public List<Blog> getAllBlogs(int page) {
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id", "title", "description", "image_path", "publish_time", "update_time",
                "category", "views_count")
                .orderBy(true,false, "update_time");
        IPage iPage = new Page(page, 12);
        blogDao.selectPage(iPage, queryWrapper);
        return iPage.getRecords();
    }

    @Override
    public long getBlogsCategoryCount() {
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        wrapper.select("distinct category");
        return blogDao.selectCount(wrapper);
    }

    @Override
    public void addViewsCount(int id) {
        Blog blog = blogDao.selectById(id);
        blog.setViewsCount(blog.getViewsCount()+1);
        blogDao.updateById(blog);
    }

    @Override
    public long getBlogCollectionCount() {
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        wrapper.select("distinct collection");
        return blogDao.selectCount(wrapper);
    }

    @Override
    public List<Map<String, Object>> getAllBlogsViewsCount() {
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        wrapper.select("sum(views_count) as sum");
        return blogDao.selectMaps(wrapper);
    }

    @Override
    public List<Blog> getBlogsByCategory(String category, int pageSize, int currentPage) {
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        wrapper.select("id", "title", "description", "content", "image_path", "views_count", "publish_time", "category")
                .eq("category", category);
        IPage iPage = new Page(currentPage, pageSize);
        blogDao.selectPage(iPage, wrapper);
        return iPage.getRecords();
    }

    @Override
    public long getBlogsCountByCategory(String category) {
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        wrapper.eq("category", category);
        return blogDao.selectCount(wrapper);
    }

    @Override
    public List<Map<String, Object>> getCollectionCount() {
        QueryWrapper wrapper = new QueryWrapper<>().select("collection as name", "count(collection) as value")
                .ne("collection", "NULL")
                .groupBy("collection");
        return blogDao.selectMaps(wrapper);
    }

    @Override
    public List<Blog> getBlogsByCollection(String collection, int pageSize, int currentPage) {
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        wrapper.eq("collection", collection);
        IPage iPage = new Page(currentPage, pageSize);
        blogDao.selectPage(iPage, wrapper);
        return iPage.getRecords();
    }

    @Override
    public long getCountByCollection(String collection) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("collection", collection);
        return blogDao.selectCount(wrapper);
    }

    @Override
    public List<Blog> getShortBlogsList(int currentPage, int pageSize) {
        QueryWrapper<Blog> wrapper = new QueryWrapper<Blog>().select("id", "title", "description",
                "update_time", "views_count", "category").orderBy(true,false, "update_time");
        if(currentPage == -1){
            return blogDao.selectList(wrapper);
        }
        else {
            IPage page = new Page(currentPage, pageSize);
            blogDao.selectPage(page, wrapper);
            return page.getRecords();
        }
    }

    @Override
    public long updateBlog(Blog blog) {
        blog.setUpdateTime(DateUtils.getCurrentTime());
        return blogDao.updateById(blog);
    }

    @Override
    public long uploadBlog(Blog blog) {
        blog.setPublishTime(DateUtils.getCurrentTime());
        blog.setUpdateTime(DateUtils.getCurrentTime());
        return blogDao.insert(blog);
    }

    @Override
    public String getLastUpdateTime() {
        Blog blog = blogDao.selectOne(new QueryWrapper<Blog>().orderBy(true, false, "update_time")
                .last("limit 1"));
        return blog.getUpdateTime();
    }

    @Override
    public List<Blog> search(String keywords) {
        int length = 200;   // 截取的长度
        List<Blog> blogList = blogDao.selectList(new QueryWrapper<Blog>().like("content", keywords));
        for(int i=0; i<blogList.size(); i++){
            int index = blogList.get(i).getContent().toUpperCase().indexOf(keywords.toUpperCase());
            int startIndex = Math.max(index - 20, 0);
            int endIndex = Math.min(startIndex + length, blogList.get(i).getContent().length());
            blogList.get(i).setContent(blogList.get(i).getContent().substring(startIndex, endIndex));
            blogList.get(i).setContent(SearchUtils.highLight(blogList.get(i).getContent(), keywords));
            blogList.get(i).setTitle(SearchUtils.highLight(blogList.get(i).getTitle(), keywords));
        }
        return blogList;
    }
}
