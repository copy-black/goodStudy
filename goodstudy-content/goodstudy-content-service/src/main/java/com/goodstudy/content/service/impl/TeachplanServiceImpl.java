package com.goodstudy.content.service.impl;

import com.goodstudy.content.mapper.TeachplanMapper;
import com.goodstudy.content.model.dto.TeachplanDto;
import com.goodstudy.content.service.TeachplanService;
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

    @Override
    public List<TeachplanDto> findTeachplanTree(Long courseId) {
        return teachplanMapper.selectTreeNodes(courseId);
    }

}
