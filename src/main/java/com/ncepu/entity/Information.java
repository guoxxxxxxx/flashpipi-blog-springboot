package com.ncepu.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "information")
public class Information {

    /**
     * 主键id
     */
    @Id
    @Column(name = "id", unique = true, columnDefinition = "int", nullable = false)
    private Integer id;

    /**
     * 网站背景图片地址
     */
    @Column(name = "background_path", columnDefinition = "varchar(512)")
    private String backgroundPath;

    /**
     * 网站域名
     */
    @Column(name = "website", columnDefinition = "varchar(512)")
    private String website;

    /**
     * 网站公告
     */
    @Column(name = "notice", columnDefinition = "varchar(512)")
    private String notice;

    /**
     * 网站总访问量
     */
    @Column(name = "views_count", columnDefinition = "int")
    private Integer viewsCount;

    /**
     * 网站创建时间
     */
    @Column(name = "create_time", columnDefinition = "Date")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;
}
