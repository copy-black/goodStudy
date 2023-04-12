package com.goodstudy.content.service.impl;

import com.goodstudy.content.model.dto.CourseBaseInfoDto;
import com.goodstudy.content.model.dto.CoursePreviewDto;
import com.goodstudy.content.model.dto.TeachplanDto;
import com.goodstudy.content.service.CourseBaseInfoService;
import com.goodstudy.content.service.CoursePublishService;
import com.goodstudy.content.service.TeachplanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description:
 *
 * @Author: Jack
 * Date: 2023/04/12 20:01
 * Version: 1.0
 */
@Service
public class CoursePublishServiceImpl implements CoursePublishService {

    @Autowired
    CourseBaseInfoService courseBaseInfoService;

    @Autowired
    TeachplanService teachplanService;


    @Override
    public CoursePreviewDto getCoursePreviewInfo(Long courseId) {
        //课程基本信息、营销信息
        CourseBaseInfoDto courseBaseInfo = courseBaseInfoService.getCourseBaseInfo(courseId);
        //课程计划信息
        List<TeachplanDto> teachplanTree = teachplanService.findTeachplanTree(courseId);
        CoursePreviewDto coursePreviewDto = new CoursePreviewDto();
        coursePreviewDto.setCourseBase(courseBaseInfo);
        coursePreviewDto.setTeachplans(teachplanTree);
        return coursePreviewDto;
    }
}
