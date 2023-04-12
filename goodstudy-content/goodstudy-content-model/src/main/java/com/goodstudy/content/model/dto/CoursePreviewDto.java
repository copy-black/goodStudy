package com.goodstudy.content.model.dto;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Description: 课程预览dto
 *
 * @Author: Jack
 * Date: 2023/04/12 19:59
 * Version: 1.0
 */
@Data
@ToString
public class CoursePreviewDto {

    /**
     * 课程基本信息,课程营销信息
     */
    CourseBaseInfoDto courseBase;

    /**
     * 课程计划信息
     */
    List<TeachplanDto> teachplans;

    // 师资信息
}
