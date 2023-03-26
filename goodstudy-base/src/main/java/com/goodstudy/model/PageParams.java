package com.goodstudy.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * Description: 分页查询通用参数
 * Author: 18488
 * Date: 2023/03/24 9:41
 * Version: 1.0
 */
@Data
@ToString
public class PageParams {
    /**
     * 当前页码
     */
    @ApiModelProperty(value = "当前页码", example = "1")
    private Long pageNo = 1L;
    /**
     * 每页记录数默认值
     */
    @ApiModelProperty(value = "每页记录数", example = "10")
    private Long pageSize = 10L;

    public PageParams() {
    }

    public PageParams(Long pageNo, Long pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }
}
