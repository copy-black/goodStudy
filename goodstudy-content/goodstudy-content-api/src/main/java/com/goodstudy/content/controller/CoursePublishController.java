package com.goodstudy.content.controller;

import com.goodstudy.content.model.dto.CoursePreviewDto;
import com.goodstudy.content.service.CoursePublishService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

/**
 * Description: 课程发布控制器
 *
 * @Author: Jack
 * Date: 2023/04/12 19:37
 * Version: 1.0
 */
@Api(value = "课程发布控制器", tags = "课程发布控制器")
@Controller
public class CoursePublishController {

    @Autowired
    private CoursePublishService coursePublishService;

    /**
     * Description: 课程预览
     *
     * @param courseId java.lang.Long
     * @return org.springframework.web.servlet.ModelAndView
     * @author Jack
     * @date 2023/4/12 20:32
     * @update_by Jack
     * @update_at 2023/4/12 20:32
     * @creed Talk is cheap, show me the comment !!!
     */
    @GetMapping("/coursepreview/{courseId}")
    public ModelAndView preview(@PathVariable("courseId") Long courseId) {
        //获取课程预览信息
        CoursePreviewDto coursePreviewInfo = coursePublishService.getCoursePreviewInfo(courseId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("model", coursePreviewInfo);
        modelAndView.setViewName("course_template");
        return modelAndView;
    }


}
