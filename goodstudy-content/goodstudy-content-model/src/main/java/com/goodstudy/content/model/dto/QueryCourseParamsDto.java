package com.goodstudy.content.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * Description: 课程查询参数Dto
 * @author 18488
 * Date: 2023/03/24 9:41
 * Version: 1.0
 */
@Data
@ToString
public class QueryCourseParamsDto {

    /**
     * 课程名称
     */
    @ApiModelProperty(value = "课程名称")
    private String courseName;
    /**
     * 审核状态
     */
    @ApiModelProperty(value = "审核状态")
    private String auditStatus;
    /**
     * 发布状态
     */
    @ApiModelProperty(value = "发布状态")
    private String publishStatus;
}
