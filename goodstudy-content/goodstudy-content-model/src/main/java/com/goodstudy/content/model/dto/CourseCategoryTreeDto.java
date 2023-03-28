package com.goodstudy.content.model.dto;

import com.goodstudy.content.model.po.CourseCategory;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Description: 课程分类树型结点dto
 *
 * @Author: Jack
 * Date: 2023/03/27 11:05
 * Version: 1.0
 */
@Data
public class CourseCategoryTreeDto extends CourseCategory implements Serializable {

    List<CourseCategoryTreeDto> childrenTreeNodes;
}
