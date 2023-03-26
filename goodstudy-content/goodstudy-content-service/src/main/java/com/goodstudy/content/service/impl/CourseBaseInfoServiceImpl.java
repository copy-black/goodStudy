package com.goodstudy.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.goodstudy.content.mapper.CourseBaseMapper;
import com.goodstudy.content.model.dto.QueryCourseParamsDto;
import com.goodstudy.content.model.po.CourseBase;
import com.goodstudy.content.service.CourseBaseInfoService;
import com.goodstudy.model.PageParams;
import com.goodstudy.model.PageResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description: 课程信息管理业务接口实现类
 *
 * @Author: Jack
 * Date: 2023/03/26 15:03
 * Version: 1.0
 */
@Service
public class CourseBaseInfoServiceImpl implements CourseBaseInfoService {

    @Autowired
    private CourseBaseMapper courseBaseMapper;

    @Override
    public PageResult<CourseBase> queryCourseBaseList(PageParams pageParams, QueryCourseParamsDto queryCourseParamsDto) {
        // 构建查询条件
        LambdaQueryWrapper<CourseBase> queryWrapper = new LambdaQueryWrapper<>();

        // 构建查询条件，根据课程名称查询
        if (StringUtils.isNotEmpty(queryCourseParamsDto.getCourseName())) {
            queryWrapper.like(CourseBase::getName, queryCourseParamsDto.getCourseName());
        }
        // 构建查询条件，根据课程审核状态查询
        if (StringUtils.isNotEmpty(queryCourseParamsDto.getAuditStatus())) {
            queryWrapper.eq(CourseBase::getAuditStatus, queryCourseParamsDto.getAuditStatus());
        }
        // 构建查询条件，根据课程发布状态查询
        if (StringUtils.isNotEmpty(queryCourseParamsDto.getPublishStatus())) {
            queryWrapper.eq(CourseBase::getStatus, queryCourseParamsDto.getPublishStatus());
        }

        // 分页对象
        Page<CourseBase> page = new Page(pageParams.getPageNo(), pageParams.getPageSize());
        // 查询
        Page<CourseBase> pageResult = courseBaseMapper.selectPage(page, queryWrapper);
        // 获取数据列表
        List<CourseBase> records = pageResult.getRecords();

        // 获得数据总数
        long total = pageResult.getTotal();
        // 构建结果集
        PageResult<CourseBase> result = new PageResult<>(records, total, pageParams.getPageNo(), pageParams.getPageSize());
        return result;
    }

}
