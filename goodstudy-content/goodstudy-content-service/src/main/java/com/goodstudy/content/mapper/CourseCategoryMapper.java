package com.goodstudy.content.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.goodstudy.content.model.dto.CourseCategoryTreeDto;
import com.goodstudy.content.model.po.CourseCategory;

import java.util.List;

/**
 * <p>
 * 课程分类 Mapper 接口
 * </p>
 *
 * @author jack
 */
public interface CourseCategoryMapper extends BaseMapper<CourseCategory> {

    /**
     * 查询课程分类树形结点
     *
     * @param id
     * @return java.util.List<com.goodstudy.content.model.dto.CourseCategoryTreeDto>
     */
    public List<CourseCategoryTreeDto> selectTreeNodes(String id);
}
