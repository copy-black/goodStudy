package com.goodstudy.content.model.dto;

import com.goodstudy.content.model.po.Teachplan;
import com.goodstudy.content.model.po.TeachplanMedia;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Description: 课程计划树形结构dto
 *
 * @Author: Jack
 * Date: 2023/03/28 22:03
 * Version: 1.0
 */
@Data
@ToString
public class TeachplanDto extends Teachplan {

    /**
     * 课程计划关联的媒资信息
     */
    TeachplanMedia teachplanMedia;

    /**
     * 子节点
     */
    List<TeachplanDto> teachPlanTreeNodes;

}
