package com.goodstudy.content.service;

import com.goodstudy.content.model.po.CourseTeacher;

import java.util.List;

/**
 * Description:
 *
 * @Author: Jack
 * Date: 2023/03/30 18:59
 * Version: 1.0
 */
public interface CourseTeacherService {

    /**
     * 获取课程教师信息接口
     *
     * @param courseId java.lang.Long
     * @return com.goodstudy.content.model.po.CourseTeacher
     * @author Jack
     * @date 2023/3/30 21:29
     * @update_by Jack
     * @update_at 2023/3/30 21:29
     * @creed Talk is cheap, show me the comment !!!
     */
    List<CourseTeacher> queryCourseTeacherById(Long courseId);

    /**
     * 创建和修改课程教师信息接口
     *
     * @param courseTeacher com.goodstudy.content.model.po.CourseTeacher
     * @return com.goodstudy.content.model.po.CourseTeacher
     * @author Jack
     * @date 2023/3/30 22:41
     * @update_by Jack
     * @update_at 2023/3/30 22:41
     * @creed Talk is cheap, show me the comment !!!
     */
    CourseTeacher saveCourseTeacher(CourseTeacher courseTeacher);

    /**
     * 删除课程教师信息接口
     *
     * @param courseId        java.lang.Long
     * @param courseTeacherId java.lang.Long
     * @return void
     * @author Jack
     * @date 2023/3/30 22:21
     * @update_by Jack
     * @update_at 2023/3/30 22:21
     * @creed Talk is cheap, show me the comment !!!
     */
    void deleteCourseTeacherById(Long courseId, Long courseTeacherId);
}
