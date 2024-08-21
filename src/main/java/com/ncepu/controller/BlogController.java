package com.ncepu.controller;


import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import com.ncepu.common.result.CommonResult;
import com.ncepu.common.ann.Logger;
import com.ncepu.common.result.PageResult;
import com.ncepu.dto.BlogQueryParams;
import com.ncepu.entity.Blog;
import com.ncepu.service.IBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/blog")
public class BlogController {
    @Autowired
    private IBlogService blogService;

    /**
     * 获取博客列表-分页获取
     */
    @GetMapping("/getAllBlogs")
    @Logger("获取博客列表")
    public CommonResult<Object> getAllBlogs(@RequestParam int pageNumber, @RequestParam int pageSize){
        PageResult<Blog> pageResult = blogService.getAllBlogs(pageNumber, pageSize);
        return new CommonResult<>().data(pageResult).success().message("获取信息成功");
    }

    /**
     * 根据id查询博客详细信息
     */
    @GetMapping("/getBlogById")
    public Blog getBlogById(@RequestParam int id){
        blogService.addViewsCount(id);
        return blogService.getById(id);
    }

    /**
     * 获取博客数量
     */
    @GetMapping("/getBlogsCount")
    public long getBlogsCount(){
        return blogService.count();
    }

    /**
     * 根据日期排序选取最近的篇文章
     */
    @GetMapping("/getRecentBlogs")
    public List<Blog> getRecentBlogs(@RequestParam int page, @RequestParam int size){
        return blogService.getRecentBlogs(page, size);
    }

    /**
     * 查询博客种类 及其对应的数量 分页查询 当页码为-1时 查询全部记录
     */
    @GetMapping("/getBlogsCategoryList")
    public List<Map<String, Object>> getBlogCategoryList(@RequestParam int page){
        return blogService.getBlogCategoryList(page);
    }

    /**
     * 查询博客种类数量
     */
    @GetMapping("/getBlogsCategoryCount")
    public long getBlogsCategoryCount(){
        return blogService.getBlogsCategoryCount();
    }

    /**
     * 查询右侧信息卡信息(该方法为其他方法的集合，一次请求可以查询出多种信息)
     */
    @GetMapping("/getCardInformation")
    public Map<String, Object> getCardInformation(){
        Map<String, Object> map = new HashMap<>();
        long blogCount = blogService.count();
        map.put("blogCount", blogCount);
        long collectionCount = blogService.getBlogCollectionCount();
        map.put("collectionCount", collectionCount);
        long categoryCount = blogService.getBlogsCategoryCount();
        map.put("categoryCount", categoryCount);
        List<Map<String, Object>> allBlogsViewsCount = blogService.getAllBlogsViewsCount();
        map.put("allViews", allBlogsViewsCount);
        return map;
    }

    /**
     * 分类查询博客
     */
    @GetMapping("/getBlogsByCategory")
    public List<Blog> getBlogsByCategory(@RequestParam String category, @RequestParam int pageSize,
                                         @RequestParam int currentPage, @RequestParam boolean isSortDesc){
        return blogService.getBlogsByCategory(category, pageSize, currentPage, isSortDesc);
    }

    /**
     * 分类查询博客的数量
     */
    @GetMapping("/getBlogsCountByCategory")
    public long getBlogsCountByCategory(@RequestParam String category){
        return blogService.getBlogsCountByCategory(category);
    }

    /**
     * 查询所有合集数量
     */
    @GetMapping("/getCollectionCount")
    public List<Map<String, Object>> getCollectionCount(){
        return blogService.getCollectionCount();
    }

    /**
     * 分类查询合集
     */
    @GetMapping("/getBlogsByCollection")
    public List<Blog> getBlogsByCollection(@RequestParam String collection, @RequestParam int pageSize,
                                         @RequestParam int currentPage, @RequestParam boolean isSortByTimeDesc,
                                           @RequestParam boolean isSortDesc){
        return blogService.getBlogsByCollection(collection, pageSize, currentPage, isSortByTimeDesc, isSortDesc);
    }

    /**
     * 获取指定合集的数量
     */
    @GetMapping("/getCountByCollection")
    public long getCountByCollection(@RequestParam String collection){
        return blogService.getCountByCollection(collection);
    }

    /**
     * 查询指定数量的博客(不包含content)
     */
    @GetMapping("/getShortBlogsList")
    @SaCheckPermission(value = {"user:modify", "user:see"}, mode = SaMode.OR)
    public List<Blog> getShortBlogsList(@RequestParam int currentPage, @RequestParam int pageSize){
        return blogService.getShortBlogsList(currentPage, pageSize);
    }

    /**
     * 根据id删除博客
     */
    @GetMapping("/deleteById")
    @SaCheckPermission("user:modify")
    public long deleteById(@RequestParam int id){
        if(blogService.removeById(id)){
            // 返回1 删除成功
            return 1;
        }
        else {
            // 返回-1 删除失败
            return -1;
        }
    }

    /**
     * 更新博客
     */
    @PostMapping("/updateBlog")
    @SaCheckPermission("user:modify")
    public long updateBlog(@RequestBody Blog blog){
        return blogService.updateBlog(blog);
    }

    /**
     * 上传新博客
     */
    @PostMapping("/uploadBlog")
    @SaCheckPermission("user:modify")
    public long uploadBlog(@RequestBody Blog blog){
        return blogService.uploadBlog(blog);
    }

    /**
     * 获取网站最后更新时间
     */
    @GetMapping("/getLastUpdateTime")
    public String getLastUpdateTime(){
        return blogService.getLastUpdateTime();
    }

    /**
     * 搜索
     */
    @GetMapping("/search")
    public List<Blog> search(@RequestParam String keywords){
        return blogService.search(keywords);
    }

    @PostMapping("/getBlogs")
    @Logger("分页获取博客数据")
    public CommonResult<Object> getBlogs(@RequestBody(required = false) BlogQueryParams params){
        if (params == null) params = new BlogQueryParams();
        Object result = blogService.getBlogs(params);
        return new CommonResult<>().success().data(result).message("查询信息成功");
    }

    /**
     * 获取集合列表
     */
    @GetMapping("/getCollectionsName")
    @Logger("获取集合列表")
    public CommonResult<Object> getCollectionsName(){
        List<String> result = blogService.getCollectionsName();
        return new CommonResult<>().data(result).success().message("查询信息成功");
    }
}
