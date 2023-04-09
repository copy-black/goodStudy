package com.goodstudy.content.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description: 课程计划与媒资文件绑定dto
 *
 * @Author: Jack
 * Date: 2023/04/09 18:14
 * Version: 1.0
 */
@Data
public class BindTeachplanMediaDto {

    @ApiModelProperty(value = "媒资文件id", required = true)
    private String mediaId;

    @ApiModelProperty(value = "课程计划id", required = true)
    private Long teachplanId;

    @ApiModelProperty(value = "媒资文件名称", required = true)
    private String fileName;
}
