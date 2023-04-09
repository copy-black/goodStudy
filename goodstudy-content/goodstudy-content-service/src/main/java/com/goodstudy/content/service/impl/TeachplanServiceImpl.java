package com.goodstudy.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.goodstudy.content.mapper.TeachplanMapper;
import com.goodstudy.content.mapper.TeachplanMediaMapper;
import com.goodstudy.content.model.dto.BindTeachplanMediaDto;
import com.goodstudy.content.model.dto.SaveTeachplanDto;
import com.goodstudy.content.model.dto.TeachplanDto;
import com.goodstudy.content.model.po.Teachplan;
import com.goodstudy.content.model.po.TeachplanMedia;
import com.goodstudy.content.service.TeachplanService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public static final int TEACHPLAN_GRADE_ONE = 1;

    public static final int TEACHPLAN_GRADE_TWO = 2;

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
        if (teachplan != null) {
            if (teachplan.getGrade() == TEACHPLAN_GRADE_ONE) {
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
            if (teachplan.getGrade() == TEACHPLAN_GRADE_TWO) {
                teachplanMapper.deleteById(teachplanId);
                // 删除小章节，同时将关联的信息进行删除，比如视频、媒资信息等
                teachplanMediaMapper.delete(new LambdaQueryWrapper<TeachplanMedia>()
                        .eq(TeachplanMedia::getTeachplanId, teachplanId));
            }
        } else {
            throw new RuntimeException("该章节不存在");
        }

    }

    @Override
    public void moveUpSubmit(Long teacherplanId) {
        Teachplan teachplan = teachplanMapper.selectById(teacherplanId);
        if (teachplan != null) {
            // 章节上移 更改排序
            int orderby = teachplan.getOrderby();
            if (orderby == 1) {
                throw new RuntimeException("该章节已经是第一章节,无法上移");
            }
            teachplan.setOrderby(orderby - 1);
            // 上一级的排序 加1
            Teachplan teachplan1 = teachplanMapper.selectOne(new LambdaQueryWrapper<Teachplan>()
                    .eq(Teachplan::getParentid, teachplan.getParentid())
                    .eq(Teachplan::getCourseId, teachplan.getCourseId())
                    .eq(Teachplan::getOrderby, orderby - 1));
            teachplan1.setOrderby(orderby);
            teachplanMapper.updateById(teachplan);
            teachplanMapper.updateById(teachplan1);
        } else {
            throw new RuntimeException("该章节不存在");
        }
    }

    @Override
    public void moveDownSubmit(Long teacherplanId) {
        Teachplan teachplan = teachplanMapper.selectById(teacherplanId);
        if (teachplan != null) {
            // 章节下移 更改排序
            int orderby = teachplan.getOrderby();
            // 最有一级的排序 不用下移
            if (orderby == getTeachplanCount(teachplan.getParentid(), teachplan.getCourseId())) {
                throw new RuntimeException("该章节已经是最后一章节,无法下移");
            }
            teachplan.setOrderby(orderby + 1);
            // 下一级的排序 减1
            Teachplan teachplan1 = teachplanMapper.selectOne(new LambdaQueryWrapper<Teachplan>()
                    .eq(Teachplan::getParentid, teachplan.getParentid())
                    .eq(Teachplan::getCourseId, teachplan.getCourseId())
                    .eq(Teachplan::getOrderby, orderby + 1));
            teachplan1.setOrderby(orderby);
            teachplanMapper.updateById(teachplan);
            teachplanMapper.updateById(teachplan1);
        } else {
            throw new RuntimeException("该章节不存在");
        }
    }

    @Override
    public void associationMedia(BindTeachplanMediaDto bindTeachplanMediaDto) {
        // 课程计划id
        Long teachplanId = bindTeachplanMediaDto.getTeachplanId();
        // 媒资id
        String mediaId = bindTeachplanMediaDto.getMediaId();

        // 校验课程计划是否存在
        Teachplan teachplan = teachplanMapper.selectById(teachplanId);
        if (teachplan == null) {
            throw new RuntimeException("课程计划不存在");
        }

        // 只允许关联叶子节点
        Integer grade = teachplan.getGrade();
        if (grade != 2) {
            throw new RuntimeException("只允许关联叶子节点");
        }

        // 课程id
        Long courseId = teachplan.getCourseId();

        // 先删除原来该教学计划绑定的媒资
        teachplanMediaMapper.delete(new LambdaQueryWrapper<TeachplanMedia>()
                .eq(TeachplanMedia::getTeachplanId, teachplanId));

        TeachplanMedia teachplanMedia = new TeachplanMedia();
        teachplanMedia.setCourseId(courseId);
        teachplanMedia.setMediaId(mediaId);
        teachplanMedia.setTeachplanId(teachplanId);
        teachplanMedia.setMediaFilename(bindTeachplanMediaDto.getFileName());
        teachplanMedia.setCreateDate(LocalDateTime.now());
        teachplanMediaMapper.insert(teachplanMedia);
    }

    @Override
    public void deleteTeachplanMedia(String  teachplanId, String mediaId) {
        // 删除课程计划媒资关联信息
        teachplanMediaMapper.delete(new LambdaQueryWrapper<TeachplanMedia>()
                .eq(TeachplanMedia::getTeachplanId, teachplanId)
                .eq(TeachplanMedia::getMediaId, mediaId));
    }

    /**
     * 取出同父同级节点的课程计划数量
     *
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
