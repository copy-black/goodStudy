package com.goodstudy.content.model.dto;

import lombok.Data;
import lombok.ToString;

/**
 * Description: 保存课程计划的dto,包括新增和修改
 *
 * @Author: Jack
 * Date: 2023/03/30 9:34
 * Version: 1.0
 */
@Data
@ToString
public class SaveTeachplanDto {

    /**
     * 课程计划id
     */
    private Long id;

    /**
     * 课程计划名称
     */
    private String pname;

    /**
     * 课程计划父id
     */
    private Long parentid;

    /**
     * 课程计划等级
     */
    private Integer grade;

    /**
     * 课程类型
     */
    private String mediaType;

    /**
     * 课程标识
     */
    private Long courseId;

    /**
     * 课程发布标识
     */
    private String coursePubId;

    /**
     * 是否支持试学或预览（试看）
     */
    private String isPreview;
}
