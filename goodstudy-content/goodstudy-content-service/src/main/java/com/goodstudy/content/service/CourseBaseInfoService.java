package com.goodstudy.content.service;

import com.goodstudy.content.model.dto.QueryCourseParamsDto;
import com.goodstudy.content.model.po.CourseBase;
import com.goodstudy.model.PageParams;
import com.goodstudy.model.PageResult;

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
     * @param pageParams
     * @param queryCourseParamsDto
     * @return
     */
    PageResult<CourseBase> queryCourseBaseList(PageParams pageParams, QueryCourseParamsDto queryCourseParamsDto);
}
