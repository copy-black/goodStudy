package com.goodstudy.content.controller;

import com.goodstudy.content.model.dto.CoursePreviewDto;
import com.goodstudy.content.service.CourseBaseInfoService;
import com.goodstudy.content.service.CoursePublishService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description: 课程公开控制器
 *
 * @Author: Jack
 * Date: 2023/04/12 20:27
 * Version: 1.0
 */
@Api(tags = "课程公开控制器")
@RestController
@RequestMapping("/open")
public class CourseOpenController {

    @Autowired
    private CourseBaseInfoService courseBaseInfoService;

    @Autowired
    private CoursePublishService coursePublishService;

    /**
     * Description: 获取课程预览信息
     *
     * @param courseId java.lang.Long
     * @return com.goodstudy.content.model.dto.CoursePreviewDto
     * @author Jack
     * @date 2023/4/12 20:32
     * @update_by Jack
     * @update_at 2023/4/12 20:32
     * @creed Talk is cheap, show me the comment !!!
     */
    @GetMapping("/course/whole/{courseId}")
    public CoursePreviewDto getPreviewInfo(@PathVariable("courseId") Long  courseId){
        return coursePublishService.getCoursePreviewInfo(courseId);
    }
}
