package com.goodstudy.content.service;

import com.goodstudy.content.model.dto.TeachplanDto;

import java.util.List;

/**
 * Description: 课程计划管理业务接口
 *
 * @Author: Jack
 * Date: 2023/03/29 11:06
 * Version: 1.0
 */
public interface TeachplanService {

    /**
     * 查询课程计划树形结构
     *
     * @param courseId java.lang.Long
     * @return java.util.List<com.goodstudy.content.model.dto.TeachplanDto>
     * @author Jack
     * @date 2023/3/29 11:07
     * @update_by Jack
     * @update_at 2023/3/29 11:07
     * @creed Talk is cheap, show me the comment !!!
     */
    public List<TeachplanDto> findTeachplanTree(Long courseId);
}
