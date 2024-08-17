package com.ncepu.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Data
@Table(name = "blog")
@Entity
public class Blog {

    /**
     * 主键
     */
    @Column(name = "id", unique = true, columnDefinition = "int", nullable = false)
    @Id
    private Integer id;

    /**
     * 标题
     */
    @Column(name = "title", columnDefinition = "varchar(32)")
    private String title;

    /**
     * 描述
     */
    @Column(name = "description", columnDefinition = "varchar(64)")
    private String description;

    /**
     * 图片路径
     */
    @Column(name = "image_path", columnDefinition = "varchar(512)")
    private String imagePath;

    /**
     * 发布时间
     */
    @Column(name = "publish_time", columnDefinition = "date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date publishTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time", columnDefinition = "date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 所属类别
     */
    @Column(name = "category", columnDefinition = "varchar(128)")
    private String category;

    /**
     * 所属集合
     */
    @Column(name = "collection", columnDefinition = "varchar(128)")
    private String collection;

    /**
     * 浏览量
     */
    @Column(name = "views_count", columnDefinition = "int")
    private Integer viewsCount;

    /**
     * 内容
     */
    @Column(name = "content", columnDefinition = "longtext")
    private String content;

    /**
     * 排序id
     */
    @Column(name = "sort_id", columnDefinition = "int")
    private Integer sortId;

    /**
     * 删除位
     */
    @Column(name = "delete_bit", columnDefinition = "bool default false")
    private Boolean deleteBit;
}
