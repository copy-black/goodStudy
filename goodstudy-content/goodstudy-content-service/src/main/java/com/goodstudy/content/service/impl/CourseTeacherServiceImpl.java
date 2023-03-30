package com.goodstudy.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.goodstudy.content.mapper.CourseTeacherMapper;
import com.goodstudy.content.model.po.CourseTeacher;
import com.goodstudy.content.service.CourseTeacherService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Description: 课程教师信息管理接口实现类
 *
 * @Author: Jack
 * Date: 2023/03/30 21:24
 * Version: 1.0
 */
@Service
public class CourseTeacherServiceImpl implements CourseTeacherService {

    @Autowired
    private CourseTeacherMapper courseTeacherMapper;

    @Override
    public List<CourseTeacher> queryCourseTeacherById(Long courseId) {
        LambdaQueryWrapper<CourseTeacher> lambdaQueryWrapper = new LambdaQueryWrapper<CourseTeacher>().eq(CourseTeacher::getCourseId, courseId);
        List<CourseTeacher> courseTeachers = courseTeacherMapper.selectList(lambdaQueryWrapper);
        return courseTeachers;
    }

    @Override
    public CourseTeacher saveCourseTeacher(CourseTeacher courseTeacher) {
        if (courseTeacher.getId() != null) {
            CourseTeacher teacher = courseTeacherMapper.selectById(courseTeacher.getId());
            if (teacher != null) {
                // 修改老师信息
                BeanUtils.copyProperties(courseTeacher, teacher);
                courseTeacherMapper.updateById(teacher);
                return teacher;
            }
        }
        CourseTeacher teacher = new CourseTeacher();
        BeanUtils.copyProperties(courseTeacher, teacher);
        teacher.setCreateDate(LocalDateTime.now());
        courseTeacherMapper.insert(teacher);
        return teacher;
    }

    @Override
    public void deleteCourseTeacherById(Long courseId, Long courseTeacherId) {
        LambdaQueryWrapper<CourseTeacher> lambdaQueryWrapper = new LambdaQueryWrapper<CourseTeacher>().eq(CourseTeacher::getCourseId, courseId).eq(CourseTeacher::getId, courseTeacherId);
        courseTeacherMapper.delete(lambdaQueryWrapper);
    }


}
