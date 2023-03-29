package com.goodstudy.content.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.goodstudy.content.model.dto.TeachplanDto;
import com.goodstudy.content.model.po.Teachplan;

import java.util.List;

/**
 * <p>
 * 课程计划 Mapper 接口
 * </p>
 *
 * @author jack
 */
public interface TeachplanMapper extends BaseMapper<Teachplan> {

    /**
     * 查询课程计划树形结构
     * @param courseId
     * @return
     */
    public List<TeachplanDto> selectTreeNodes(Long courseId);
}
