package com.ncepu.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Blog {
    private Integer id;
    private String title;
    private String description;
    private String imagePath;
    private String publishTime;
    private String updateTime;
    private String category;
    private String collection;
    private Integer viewsCount;
    private String content;
}
