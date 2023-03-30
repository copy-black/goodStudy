package com.goodstudy.content.service;

import com.goodstudy.content.model.dto.SaveTeachplanDto;
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

    /**
     * 课程计划创建或修改
     *
     * @param saveTeachplanDto com.goodstudy.content.model.dto.SaveTeachplanDto
     * @return void
     * @author Jack
     * @date 2023/3/30 9:42
     * @update_by Jack
     * @update_at 2023/3/30 9:42
     * @creed Talk is cheap, show me the comment !!!
     */
    void saveTeachplan(SaveTeachplanDto saveTeachplanDto);

    /**
     * 课程计划删除
     *
     * @param teachplanId java.lang.Long
     * @return void
     * @author Jack
     * @date 2023/3/30 15:10
     * @update_by Jack
     * @update_at 2023/3/30 15:10
     * @creed Talk is cheap, show me the comment !!!
     */
    void deleteTeachplan(Long teachplanId);

    /**
     * 课程计划上移
     *
     * @param teacherplanId java.lang.Long
     * @return void
     * @author Jack
     * @date 2023/3/30 15:57
     * @update_by Jack
     * @update_at 2023/3/30 15:57
     * @creed Talk is cheap, show me the comment !!!
     */
    void moveUpSubmit(Long teacherplanId);

    /**
     * 课程计划下移
     *
     * @param teacherplanId java.lang.Long
     * @return void
     * @author Jack
     * @date 2023/3/30 16:20
     * @update_by Jack
     * @update_at 2023/3/30 16:20
     * @creed Talk is cheap, show me the comment !!!
     */
    void moveDownSubmit(Long teacherplanId);
}
