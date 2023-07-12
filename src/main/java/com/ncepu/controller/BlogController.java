package com.ncepu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ncepu.entity.Blog;
import com.ncepu.service.IBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/blog")
public class BlogController {
    @Autowired
    private IBlogService blogService;

    /**
     * 获取博客列表-分页获取
     */
    @GetMapping("/getAllBlogs")
    public List<Blog> getAllBlogs(@RequestParam int page){
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id", "title", "description", "image_path", "publish_time", "update_time",
                "category", "views_count").eq("is_delete", 0);
        IPage iPage = new Page(page, 12);
        blogService.page(iPage, queryWrapper);
        return iPage.getRecords();
    }

    /**
     * 根据id查询博客详细信息
     */
    @GetMapping("/getBlogById")
    public Blog getBlogById(@RequestParam int id){
        return blogService.getById(id);
    }

    /**
     * 获取博客数量
     */
    @GetMapping("/getBlogsCount")
    public long getBlogsCount(){
        return blogService.count();
    }
}
