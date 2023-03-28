package com.goodstudy.content.controller;

import com.goodstudy.content.model.dto.CourseCategoryTreeDto;
import com.goodstudy.content.service.CourseCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Description: 课程分类管理接口
 *
 * @Author: Jack
 * Date: 2023/03/27 11:07
 * Version: 1.0
 */
@Api(value = "课程分类管理接口", tags = "课程分类管理接口")
@Slf4j
@RestController
public class CourseCategoryController {

    @Autowired
    private CourseCategoryService courseCategoryService;

    /**
     * 课程分类树形结构查询
     *
     * @param
     * @return java.util.List<com.goodstudy.content.model.dto.CourseCategoryTreeDto>
     * @author Jack
     * @date 2023/3/27 11:08
     * @update_by Jack
     * @update_at 2023/3/27 11:08
     * @creed Talk is cheap, show me the comment !!!
     */
    @ApiOperation("课程分类树形结构查询")
    @GetMapping("/course-category/tree-nodes")
    public List<CourseCategoryTreeDto> queryTreeNodes() {
        return courseCategoryService.queryTreeNodes("1");
    }
}
