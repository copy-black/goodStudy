package com.goodstudy.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * Description: 分页查询结果模型类
 *
 * @author: 18488
 * Date: 2023/03/24 16:03
 * Version: 1.0
 */
@Data
@ToString
public class PageResult<T> implements Serializable {

    /**
     * 数据列表
     */
    private List<T> items;
    /**
     * 总记录数
     */
    private Long counts;
    /**
     * 当前页码
     */
    private Long pageNo;
    /**
     * 每页记录数
     */
    private Long pageSize;

    public PageResult(List<T> items, Long counts, Long pageNo, Long pageSize) {
        this.items = items;
        this.counts = counts;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }
}
