package com.ncepu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ncepu.dao.BlogDao;
import com.ncepu.entity.Blog;
import com.ncepu.service.IBlogService;
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
                "category", "views_count").eq("is_delete", 0);
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
}
