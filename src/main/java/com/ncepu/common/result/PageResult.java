/**
 * @Time: 2024/8/16 17:47
 * @Author: guoxun
 * @File: PageResult
 * @Description:
 */

package com.ncepu.common.result;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.util.List;

@Data
public class PageResult<T>{

    public PageResult(IPage<T> page){
        this.total = page.getTotal();
        this.currentPage = page.getCurrent();
        this.pageSize = page.getSize();
        this.data = page.getRecords();
    }

    // 总条数
    private Long total;
    // 当前页码
    private Long currentPage;
    // 页面大小
    private Long pageSize;
    // 数据
    private List<T> data;
}
