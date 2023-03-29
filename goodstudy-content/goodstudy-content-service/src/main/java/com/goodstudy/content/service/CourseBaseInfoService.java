package com.goodstudy.content.service;

import com.goodstudy.content.model.dto.AddCourseDto;
import com.goodstudy.content.model.dto.CourseBaseInfoDto;
import com.goodstudy.content.model.dto.EditCourseDto;
import com.goodstudy.content.model.dto.QueryCourseParamsDto;
import com.goodstudy.content.model.po.CourseBase;
import com.goodstudy.base.model.PageParams;
import com.goodstudy.base.model.PageResult;

/**
 * Description: 课程基本信息管理业务接口
 *
 * @Author: Jack
 * Date: 2023/03/26 15:00
 * Version: 1.0
 */
public interface CourseBaseInfoService {

    /**
     * 分页查询课程基本信息
     *
     * @param pageParams           com.goodstudy.model.PageParams 分页查询通用参数
     * @param queryCourseParamsDto com.goodstudy.content.model.dto.QueryCourseParamsDto
     * @return com.goodstudy.model.PageResult<com.goodstudy.content.model.po.CourseBase>
     * @author Jack
     * @date 2023/3/28 15:11
     * @update_by Jack
     * @update_at 2023/3/28 15:11
     * @creed Talk is cheap, show me the comment !!!
     */
    PageResult<CourseBase> queryCourseBaseList(PageParams pageParams, QueryCourseParamsDto queryCourseParamsDto);

    /**
     * 添加课程基本信息
     *
     * @param companyId    java.lang.Long 教学机构id
     * @param addCourseDto com.goodstudy.content.model.dto.AddCourseDto 课程基本信息
     * @return com.goodstudy.content.model.dto.CourseBaseInfoDto
     * @author Jack
     * @date 2023/3/28 15:10
     * @update_by Jack
     * @update_at 2023/3/28 15:10
     * @creed Talk is cheap, show me the comment !!!
     */
    CourseBaseInfoDto createCourseBase(Long companyId, AddCourseDto addCourseDto);

    /**
     * 根据课程id查询课程基础信息
     *
     * @param courseId java.lang.Long
     * @return com.goodstudy.content.model.dto.CourseBaseInfoDto
     * @author Jack
     * @date 2023/3/29 15:48
     * @update_by Jack
     * @update_at 2023/3/29 15:48
     * @creed Talk is cheap, show me the comment !!!
     */
    CourseBaseInfoDto getCourseBaseInfo(Long courseId);

    /**
     * 修改课程基本信息
     *
     * @param editCourseDto com.goodstudy.content.model.dto.EditCourseDto
     * @return com.goodstudy.content.model.dto.CourseBaseInfoDto
     * @author Jack
     * @date 2023/3/29 16:09
     * @update_by Jack
     * @update_at 2023/3/29 16:09
     * @creed Talk is cheap, show me the comment !!!
     */
    CourseBaseInfoDto updateCourseBase(Long companyId, EditCourseDto editCourseDto);


}
