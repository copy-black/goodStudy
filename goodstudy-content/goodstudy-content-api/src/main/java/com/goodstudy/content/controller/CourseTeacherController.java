package com.goodstudy.content.controller;

import com.goodstudy.content.model.po.CourseTeacher;
import com.goodstudy.content.service.CourseTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Description: 课程教师信息管理接口
 *
 * @Author: Jack
 * Date: 2023/03/30 18:58
 * Version: 1.0
 */
@Api(value = "课程教师信息编辑接口", tags = "课程教师信息编辑接口")
@RestController
public class CourseTeacherController {

    @Autowired
    private CourseTeacherService courseTeacherService;

    /**
     * 获取课程教师信息接口
     *
     * @param
     * @return void
     * @author Jack
     * @date 2023/3/30 21:25
     * @update_by Jack
     * @update_at 2023/3/30 21:25
     * @creed Talk is cheap, show me the comment !!!
     */
    @ApiOperation("获取课程教师信息接口")
    @GetMapping("/courseTeacher/list/{courseId}")
    public List<CourseTeacher> getCourseTeacherById(@PathVariable Long courseId) {
        return courseTeacherService.queryCourseTeacherById(courseId);
    }

    /**
     * 创建课程教师信息接口
     *
     * @param courseTeacher com.goodstudy.content.model.dto.AddCourseTeacherDto
     * @return com.goodstudy.content.model.po.CourseTeacher
     * @author Jack
     * @date 2023/3/30 22:11
     * @update_by Jack
     * @update_at 2023/3/30 22:11
     * @creed Talk is cheap, show me the comment !!!
     */
    @ApiOperation("创建课程教师信息接口")
    @PostMapping("/courseTeacher")
    public CourseTeacher saveCourseTeacher(@RequestBody CourseTeacher courseTeacher) {
        Long companyId = 1232141425L;
        return courseTeacherService.saveCourseTeacher(courseTeacher);
    }

    /**
     * 删除课程教师信息接口
     *
     * @param courseId        java.lang.Long
     * @param courseTeacherId java.lang.Long
     * @return void
     * @author Jack
     * @date 2023/3/30 22:23
     * @update_by Jack
     * @update_at 2023/3/30 22:23
     * @creed Talk is cheap, show me the comment !!!
     */
    @ApiOperation("删除课程教师信息接口")
    @DeleteMapping("/courseTeacher/course/{courseId}/{courseTeacherId}")
    public void deleteCourseTeacherById(@PathVariable Long courseId, @PathVariable Long courseTeacherId) {
        courseTeacherService.deleteCourseTeacherById(courseId, courseTeacherId);
    }
}
