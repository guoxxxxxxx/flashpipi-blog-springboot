/**
 * @Time: 2024/8/16 21:17
 * @Author: guoxun
 * @File: BlogQueryParams
 * @Description:
 */

package com.ncepu.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class BlogQueryParams {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 描述
     */
    private String description;

    /**
     * 图片路径
     */
    private String imagePath;

    /**
     * 发布时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date publishTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startPublishTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endPublishTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startUpdateTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endUpdateTime;

    /**
     * 所属类别
     */
    private String category;

    /**
     * 所属集合
     */
    private String collection;

    /**
     * 浏览量
     */
    private Integer viewsCount;

    /**
     * 内容
     */
    private String content;

    /**
     * 排序id
     */
    private Integer sortId;

    /**
     * 删除位
     */
    private Boolean deleteBit;

    /**
     * 排序字段
     */
    private String sortKey = "sortId";

    /**
     * 页码
     */
    private Integer pageNumber;

    /**
     * 页面大小
     */
    private Integer pageSize;

    /**
     * 是否降序排序
     * 默认为升序排序
     */
    private Boolean desc = false;
}
