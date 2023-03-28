package com.goodstudy.content.service;

import com.goodstudy.content.model.dto.CourseCategoryTreeDto;

import java.util.List;

/**
 * Description: 课程分类管理业务接口
 *
 * @Author: Jack
 * Date: 2023/03/27 11:35
 * Version: 1.0
 */
public interface CourseCategoryService {

    /**
     * 课程分类树形结构查询
     *
     * @param
     * @return java.util.List<com.goodstudy.content.model.dto.CourseCategoryTreeDto>
     * @author Jack
     * @date 2023/3/27 11:38
     * @update_by Jack
     * @update_at 2023/3/27 11:38
     * @creed Talk is cheap, show me the comment !!!
     */
    public List<CourseCategoryTreeDto> queryTreeNodes(String id);

}
