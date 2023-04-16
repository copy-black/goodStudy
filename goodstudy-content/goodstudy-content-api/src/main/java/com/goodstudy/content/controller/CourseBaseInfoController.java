package com.goodstudy.content.controller;

import com.goodstudy.base.exception.ValidationGroups;
import com.goodstudy.content.model.dto.AddCourseDto;
import com.goodstudy.content.model.dto.CourseBaseInfoDto;
import com.goodstudy.content.model.dto.EditCourseDto;
import com.goodstudy.content.model.dto.QueryCourseParamsDto;
import com.goodstudy.content.model.po.CourseBase;
import com.goodstudy.content.service.CourseBaseInfoService;
import com.goodstudy.base.model.PageParams;
import com.goodstudy.base.model.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Description: 课程信息编辑接口
 *
 * @Author: Jack
 * Date: 2023/03/25 18:01
 * Version: 1.0
 */
@Api(value = "课程信息编辑接口", tags = "课程信息编辑接口")
@RestController
public class CourseBaseInfoController {

    @Autowired
    private CourseBaseInfoService courseBaseInfoService;

    /**
     * 课程查询接口
     *
     * @param pageParams        com.goodstudy.model.PageParams
     * @param queryCourseParams com.goodstudy.content.model.dto.QueryCourseParamsDto
     * @return com.goodstudy.model.PageResult<com.goodstudy.content.model.po.CourseBase>
     * @author Jack
     * @date 2023/3/26 15:08
     * @update_by Jack
     * @update_at 2023/3/26 15:08
     * @creed Talk is cheap, show me the comment !!!
     */
    @ApiOperation("课程查询接口")
    @PostMapping("/course/list")
    public PageResult<CourseBase> listCourse(PageParams pageParams, @RequestBody QueryCourseParamsDto queryCourseParams) {
        return courseBaseInfoService.queryCourseBaseList(pageParams, queryCourseParams);
    }

    /**
     * 添加课程接口
     *
     * @param addCourse com.goodstudy.content.model.dto.AddCourseDto
     * @return com.goodstudy.content.model.dto.CourseBaseInfoDto
     * @author Jack
     * @date 2023/3/28 15:06
     * @update_by Jack
     * @update_at 2023/3/28 15:06
     * @creed Talk is cheap, show me the comment !!!
     */
    @ApiOperation("添加课程接口")
    @PostMapping("/course")
    public CourseBaseInfoDto createCourseBase(@RequestBody @Validated({ValidationGroups.Inster.class}) AddCourseDto addCourse) {
        // 机构ID
        Long companyId = 1232141425L;
        return courseBaseInfoService.createCourseBase(companyId, addCourse);
    }

    /**
     * 根据课程id查询课程基础信息接口
     *
     * @param courseId java.lang.Long
     * @return com.goodstudy.content.model.dto.CourseBaseInfoDto
     * @author Jack
     * @date 2023/3/29 15:46
     * @update_by Jack
     * @update_at 2023/3/29 15:46
     * @creed Talk is cheap, show me the comment !!!
     */
    @ApiOperation("根据课程id查询课程基础信息")
    @GetMapping("/course/{courseId}")
    public CourseBaseInfoDto getCourseBaseById(@PathVariable Long courseId) {
        //取出当前用户身份
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return courseBaseInfoService.getCourseBaseInfo(courseId);
    }

    /**
     * 修改课程基础信息接口
     *
     * @param editCourseDto com.goodstudy.content.model.dto.EditCourseDto
     * @return com.goodstudy.content.model.dto.CourseBaseInfoDto
     * @author Jack
     * @date 2023/3/29 16:06
     * @update_by Jack
     * @update_at 2023/3/29 16:06
     * @creed Talk is cheap, show me the comment !!!
     */
    @ApiOperation("修改课程基础信息")
    @PutMapping("/course")
    public CourseBaseInfoDto modifyCourseBase(@RequestBody EditCourseDto editCourseDto) {
        Long companyId = 1232141425L;
        return courseBaseInfoService.updateCourseBase(companyId,editCourseDto);
    }

    /**
     * 删除课程接口
     *
     * @param courseId java.lang.Long
     * @return void
     * @author Jack
     * @date 2023/3/30 22:54
     * @update_by Jack
     * @update_at 2023/3/30 22:54
     * @creed Talk is cheap, show me the comment !!!
     */
    @ApiOperation("删除课程接口")
    @DeleteMapping("/course/{courseId}")
    public void deleteCourseBase(Long courseId) {
        courseBaseInfoService.deleteCourseBase(courseId);
    }


}
