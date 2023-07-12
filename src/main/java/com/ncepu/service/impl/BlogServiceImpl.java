package com.ncepu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ncepu.dao.BlogDao;
import com.ncepu.entity.Blog;
import com.ncepu.service.IBlogService;
import org.springframework.stereotype.Service;

@Service
public class BlogServiceImpl extends ServiceImpl<BlogDao, Blog> implements IBlogService {
}
