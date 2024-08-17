/**
 * @Time: 2024/8/16 17:47
 * @Author: guoxun
 * @File: PageResult
 * @Description:
 */

package com.ncepu.common.result;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

@Data
public class PageResult<T> {
    // 总条数
    private Integer total;
    // 当前页码
    private Integer currentPage;
    // 页面大小
    private Integer pageSize;
    // 数据
    private IPage<T> data;
}
