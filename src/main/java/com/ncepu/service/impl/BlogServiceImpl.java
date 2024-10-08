package com.ncepu.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ncepu.common.result.PageResult;
import com.ncepu.dao.BlogDao;
import com.ncepu.dto.BlogQueryParams;
import com.ncepu.entity.Blog;
import com.ncepu.service.IBlogService;
import com.ncepu.utils.DateUtils;
import com.ncepu.utils.SearchUtils;
import com.ncepu.utils.template.ExternalRestTemplate;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class BlogServiceImpl extends ServiceImpl<BlogDao, Blog> implements IBlogService {
    @Autowired
    BlogDao blogDao;
    @Autowired
    ExternalRestTemplate externalRestTemplate;
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
    public PageResult<Blog> getAllBlogs(int pageNumber, int pageSize) {
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id", "title", "description", "image_path", "publish_time", "update_time",
                "category", "views_count")
                .orderBy(true,false, "publish_time");
        Page<Blog> blogPage = baseMapper.selectPage(new Page<>(pageNumber, pageSize), queryWrapper);
        return new PageResult<>(blogPage);
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
    public List<Blog> getBlogsByCategory(String category, int pageSize, int currentPage, boolean isSortDesc) {
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        wrapper.select("id", "title", "description", "content", "image_path", "views_count", "publish_time", "category")
                .eq("category", category);
        if(isSortDesc){
            wrapper.orderByDesc("publish_time");
        }
        else{
            wrapper.orderByAsc("publish_time");
        }
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
    public List<Blog> getBlogsByCollection(String collection, int pageSize, int currentPage, boolean isSortByTimeDesc,
                                           boolean isSortDesc) {
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        wrapper.eq("collection", collection);
        if (isSortDesc){
            wrapper.orderByDesc("sort_id");
        }
        else {
            wrapper.orderByAsc("sort_id");
        }
        if (isSortByTimeDesc){
            wrapper.orderByDesc("publish_time");
        }
        else {
            wrapper.orderByAsc("publish_time");
        }
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
        blog.setUpdateTime(new Date());
        if (blog.getImagePath().equals("")){
            blog.setImagePath(externalRestTemplate.getRandomPicUrl());
        }
        return blogDao.updateById(blog);
    }

    @Override
    public long uploadBlog(Blog blog) {
        Long count = baseMapper.selectCount(new QueryWrapper<>());
        blog.setSortId(Integer.parseInt(String.valueOf(count)));
        if(blog.getPublishTime() == null)
            blog.setPublishTime(new Date());
        if (blog.getDescription() == null)
            blog.setDescription(blog.getTitle());
        blog.setUpdateTime(new Date());
        if(blog.getImagePath() == null || blog.getImagePath().equals("")){
            String picUrl = externalRestTemplate.getRandomPicUrl();
            blog.setImagePath(picUrl);
        }
        return blogDao.insert(blog);
    }

    @Override
    public String getLastUpdateTime() {
        Blog blog = blogDao.selectOne(new QueryWrapper<Blog>().orderBy(true, false, "update_time")
                .last("limit 1"));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(blog.getUpdateTime());
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

    @Override
    public Object getBlogs(BlogQueryParams params) {
        if (params.getPageSize() == null || params.getPageNumber() == null){
            Map<String, Object> result = new HashMap<>();
            List<Blog> blogs = baseMapper.queryByCondition(params);
            result.put("blogs", blogs);
            return result;
        }
        else {
            IPage<Blog> blogIPage = baseMapper.queryByCondition(new Page<>(params.getPageNumber(), params.getPageSize()), params);
            return new PageResult<>(blogIPage);
        }
    }

    @Override
    public List<String> getCollectionsName() {
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        wrapper.select("collection").groupBy("collection");
        List<Blog> blogs = baseMapper.selectList(wrapper);
        List<String> result = new ArrayList<>();
        for (Blog e : blogs){
            result.add(e.getCollection());
        }
        return result;
    }

    @Override
    public Blog parseFileContent(MultipartFile textFile) throws IOException {
        String filename = textFile.getOriginalFilename();
        assert filename != null;
        String fileType = filename.split("\\.")[1];
        assert fileType.equals("md") || fileType.equals("txt");
        String content = new String(textFile.getBytes());
        String title = content.substring(content.indexOf("# ")).split("\n")[0].replace("# ", "");
        Blog blog = new Blog();
        blog.setContent(content);
        blog.setTitle(title);
        blog.setImagePath(externalRestTemplate.getRandomPicUrl());
        return blog;
    }

    @Override
    public boolean checkBlogExistByTitle(String title) {
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        wrapper.eq("title", title);
        Long count = baseMapper.selectCount(wrapper);
        return !(count == 0);
    }

    @Override
    public boolean updateBlogByTitle(Blog blog) {
        int count = baseMapper.update(blog, new QueryWrapper<Blog>().eq("title", blog.getTitle()));
        return !(count == 0);
    }

    @Override
    public String getRandomImgUrl() {
        return externalRestTemplate.getRandomPicUrl();
    }

    @Override
    public void downloadById(Integer id, HttpServletResponse response) throws IOException {
        Blog blog = baseMapper.selectById(id);
        Map<String, Object> detail = new HashMap<>();
        detail.put("category", blog.getCategory());
        detail.put("collection", blog.getCollection());
        detail.put("description", blog.getDescription());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String filename = blog.getTitle().replace(" ", "").replace("\r", "") + "--" + simpleDateFormat.format(blog.getPublishTime()) + ".md";
        String content = JSON.toJSONString(detail) + "\n" + blog.getContent();
        byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=" + URLEncoder.encode(filename, StandardCharsets.UTF_8);
        response.setHeader("Content-Disposition", contentDisposition);

        // 设置内容类型
        response.setContentType("text/plain; charset=UTF-8");

        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(bytes);
    }

    @Override
    public void downloadZip(HttpServletResponse response) throws IOException {
        List<Blog> blogs = baseMapper.selectList(new QueryWrapper<>());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment; filename=flashpipi-blog-" + dateFormat.format(new Date()) + ".zip");

        String tempDir = "./temp/tempFiles/";
        Path tempDirPath = Paths.get(tempDir);
        Files.createDirectories(tempDirPath);
        // 将每个字符串保存到md中
        for (Blog e : blogs){
            String filename = (e.getTitle() + "--" + dateFormat.format(e.getPublishTime()) + ".md")
                    .replace(" ", "").replace("\r", "")
                    .replace("|", "-");
            Map<String, Object> prefixMap = new HashMap<>();
            prefixMap.put("category", e.getCategory());
            prefixMap.put("collection", e.getCollection());
            prefixMap.put("description", e.getDescription());
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempDir + filename))){
                writer.write(JSON.toJSONString(prefixMap) + "\n" + e.getContent());
            }
        }

        // 压缩文件
        try (ZipOutputStream zos = new ZipOutputStream(response.getOutputStream())){
            Files.walk(tempDirPath).filter(path -> !Files.isDirectory(path)).forEach(
                    path -> {
                        ZipEntry zipEntry = new ZipEntry(tempDirPath.relativize(path).toString());
                        try {
                            zos.putNextEntry(zipEntry);
                            Files.copy(path, zos);
                            zos.closeEntry();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
            );
        }
        finally {
            // 清理临时文件
            Files.walk(tempDirPath).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
        }
    }
}
