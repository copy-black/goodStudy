package com.goodstudy.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.goodstudy.content.mapper.TeachplanMapper;
import com.goodstudy.content.mapper.TeachplanMediaMapper;
import com.goodstudy.content.model.dto.SaveTeachplanDto;
import com.goodstudy.content.model.dto.TeachplanDto;
import com.goodstudy.content.model.po.Teachplan;
import com.goodstudy.content.model.po.TeachplanMedia;
import com.goodstudy.content.service.TeachplanService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description: 课程计划管理业务接口实现类
 *
 * @Author: Jack
 * Date: 2023/03/29 11:06
 * Version: 1.0
 */
@Service
public class TeachplanServiceImpl implements TeachplanService {

    @Autowired
    private TeachplanMapper teachplanMapper;

    @Autowired
    private TeachplanMediaMapper teachplanMediaMapper;

    @Override
    public List<TeachplanDto> findTeachplanTree(Long courseId) {
        return teachplanMapper.selectTreeNodes(courseId);
    }

    @Override
    public void saveTeachplan(SaveTeachplanDto saveTeachplanDto) {
        Long id = saveTeachplanDto.getId();
        // 判断是否有id，有则修改，无则新增
        if (id != null) {
            Teachplan teachplan = teachplanMapper.selectById(id);
            BeanUtils.copyProperties(saveTeachplanDto, teachplan);
            teachplanMapper.updateById(teachplan);
        } else {
            Teachplan teachplan = new Teachplan();
            BeanUtils.copyProperties(saveTeachplanDto, teachplan);
            // 取出同父同级节点的课程计划数量
            int teachplanCount = getTeachplanCount(saveTeachplanDto.getParentid(), saveTeachplanDto.getCourseId());
            // 设置课程计划的级别
            teachplan.setOrderby(teachplanCount + 1);
            teachplanMapper.insert(teachplan);
        }
    }

    @Override
    public void deleteTeachplan(Long teachplanId) {
        // 删除大章节，大章节下有小章节时不允许删除
        Teachplan teachplan = teachplanMapper.selectById(teachplanId);
        if (teachplan.getGrade().equals("1")) {
            int teachplanCount = getTeachplanCount(teachplanId, teachplan.getCourseId());
            if (teachplanCount > 0) {
                throw new RuntimeException("该章节下有小章节，不允许删除");
            } else {
                // 删除大章节，大单节下没有小章节时可以正常删除
                teachplanMapper.deleteById(teachplanId);
                teachplanMediaMapper.delete(new LambdaQueryWrapper<TeachplanMedia>()
                        .eq(TeachplanMedia::getTeachplanId, teachplanId));
            }
        }
        // 删除小章节，同时将关联的信息进行删除，比如视频、媒资信息等
        if (teachplan.getGrade().equals("2")) {
            teachplanMapper.deleteById(teachplanId);
            // 删除小章节，同时将关联的信息进行删除，比如视频、媒资信息等
            teachplanMediaMapper.delete(new LambdaQueryWrapper<TeachplanMedia>()
                    .eq(TeachplanMedia::getTeachplanId, teachplanId));
        }
    }

    /**
     * 取出同父同级节点的课程计划数量
     * @param parentId
     * @param courseId
     */
    private int getTeachplanCount(Long parentId, Long courseId) {
        LambdaQueryWrapper<Teachplan> eq = new LambdaQueryWrapper<Teachplan>()
                .eq(Teachplan::getParentid, parentId)
                .eq(Teachplan::getCourseId, courseId);
        return teachplanMapper.selectCount(eq);
    }
}
