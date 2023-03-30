package com.goodstudy.content.controller;

import com.goodstudy.content.model.dto.SaveTeachplanDto;
import com.goodstudy.content.model.dto.TeachplanDto;
import com.goodstudy.content.service.TeachplanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Description: 课程计划接口
 *
 * @Author: Jack
 * Date: 2023/03/28 21:55
 * Version: 1.0
 */
@Api(value = "课程计划接口", tags = "课程计划接口")
@RestController
public class TeachplanController {

    @Autowired
    private TeachplanService teachplanService;

    /**
     * 查询课程计划树形结构
     *
     * @param
     * @return void
     * @author Jack
     * @date 2023/3/28 21:58
     * @update_by Jack
     * @update_at 2023/3/28 21:58
     * @creed Talk is cheap, show me the comment !!!
     */
    @ApiOperation("查询课程计划树形结构")
    @ApiImplicitParam(value = "courseId", name = "课程Id", required = true, dataType = "Long", paramType = "path")
    @GetMapping("/teachplan/{courseId}/tree-nodes")
    public List<TeachplanDto> listTreeNodes(@PathVariable Long courseId) {
        return teachplanService.findTeachplanTree(courseId);
    }

    /**
     * 课程计划创建或修改
     *
     * @param saveTeachplanDto com.goodstudy.content.model.dto.saveTeachplanDto
     * @return void
     * @author Jack
     * @date 2023/3/30 9:42
     * @update_by Jack
     * @update_at 2023/3/30 9:42
     * @creed Talk is cheap, show me the comment !!!
     */
    @ApiOperation("课程计划创建或修改")
    @PostMapping("/teachplan")
    public void saveTeachplan(@RequestBody SaveTeachplanDto saveTeachplanDto) {
        teachplanService.saveTeachplan(saveTeachplanDto);
    }

    /**
     * 课程计划删除
     *
     * @param teachplanId java.lang.Long
     * @return void
     * @author Jack
     * @date 2023/3/30 15:10
     * @update_by Jack
     * @update_at 2023/3/30 15:10
     * @creed Talk is cheap, show me the comment !!!
     */
    @ApiOperation("课程计划删除")
    @DeleteMapping("/teachplan/{teachplanId}")
    public void deleteTeachplan(@PathVariable Long teachplanId) {
        teachplanService.deleteTeachplan(teachplanId);
    }

}
