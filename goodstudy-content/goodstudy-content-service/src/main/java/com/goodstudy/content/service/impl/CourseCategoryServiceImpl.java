package com.goodstudy.content.service.impl;

import com.goodstudy.content.mapper.CourseCategoryMapper;
import com.goodstudy.content.model.dto.CourseCategoryTreeDto;
import com.goodstudy.content.service.CourseCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Description: 课程分类管理业务接口实现类
 *
 * @Author: Jack
 * Date: 2023/03/27 11:36
 * Version: 1.0
 */
@Service
public class CourseCategoryServiceImpl implements CourseCategoryService {

    @Autowired
    private CourseCategoryMapper courseCategoryMapper;

    @Override
    public List<CourseCategoryTreeDto> queryTreeNodes(String id) {
        // 获取到所有的课程分类
        List<CourseCategoryTreeDto> courseCategoryTreeDtos = courseCategoryMapper.selectTreeNodes(id);

        // 将list转map, 以备使用,排除根节点
        Map<String, CourseCategoryTreeDto> collect = courseCategoryTreeDtos.stream().filter(item -> !id.equals(item.getId()))
                .collect(Collectors.toMap(CourseCategoryTreeDto::getId, Function.identity()));

        // 创建返回结果
        List<CourseCategoryTreeDto> result = new ArrayList<>();
        // 遍历所有的课程分类
        courseCategoryTreeDtos.stream().filter(item -> !id.equals(item.getId())).forEach(item -> {
            // 判断item.getParentId()是否等于id
            if (item.getParentid().equals(id)) {
                // 如果等于,则将item添加到result中
                result.add(item);// 相当于 id 下面的子节点
            }
            // 得到当前节点父节点
            CourseCategoryTreeDto parent = collect.get(item.getParentid());
            // 判断父节点是否为空
            if (parent != null) {
                // 判断子节点是否为空
                if (parent.getChildrenTreeNodes() == null) {
                    parent.setChildrenTreeNodes(new ArrayList<>());
                }
                // 将item添加到父节点的子节点中
                parent.getChildrenTreeNodes().add(item);
            }
        });
        return result;
    }
}
