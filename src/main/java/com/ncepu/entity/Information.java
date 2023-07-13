package com.ncepu.entity;

import lombok.Data;

@Data
public class Information {
    private Integer id;
    private String backgroundPath;
    private String website;
    private String updateTime;
    private String notice;
    private Integer viewsCount;
    private String createTime;
}
