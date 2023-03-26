package com.goodstudy.content.controller;

import com.goodstudy.content.model.dto.QueryCourseParamsDto;
import com.goodstudy.content.model.po.CourseBase;
import com.goodstudy.content.service.CourseBaseInfoService;
import com.goodstudy.model.PageParams;
import com.goodstudy.model.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
     * TODO: 2023/3/26 15:08  请添加方法描述
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
    public PageResult<CourseBase> list(PageParams pageParams, @RequestBody QueryCourseParamsDto queryCourseParams) {
        PageResult<CourseBase> pageResult = courseBaseInfoService.queryCourseBaseList(pageParams, queryCourseParams);
        return pageResult;
    }

}
