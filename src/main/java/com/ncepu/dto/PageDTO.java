/**
 * @Time: 2024/8/16 21:12
 * @Author: guoxun
 * @File: PageDTO
 * @Description:
 */

package com.ncepu.dto;

import lombok.Data;

@Data
public class PageDTO {

    private Integer pageSize;
    private Integer pageNumber;
    private Boolean desc = false;       // 是否降序排序，默认false
    private String sortKeyWords;        // 排序字段
}
