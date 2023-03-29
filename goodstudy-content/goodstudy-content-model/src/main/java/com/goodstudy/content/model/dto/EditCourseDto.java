package com.goodstudy.content.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description: 编辑课程基本信息dto
 *
 * @Author: Jack
 * Date: 2023/03/29 16:04
 * Version: 1.0
 */
@Data
@ApiModel(description = "编辑课程基本信息", value = "EditCourseDto")
public class EditCourseDto extends AddCourseDto {

    /**
     * 课程id
     */
    @ApiModelProperty(value = "课程id", required = true)
    private Long id;
}
