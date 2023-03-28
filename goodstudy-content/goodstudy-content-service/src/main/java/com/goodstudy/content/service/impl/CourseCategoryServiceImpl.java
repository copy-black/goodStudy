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
 * Description:
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
        List<CourseCategoryTreeDto> courseCategoryTreeDtos = courseCategoryMapper.selectTreeNodes(id);
        // 将list转map, 以备使用,排除根节点
        Map<String, CourseCategoryTreeDto> courseCategoryTreeDtoMap = courseCategoryTreeDtos.stream()
                .filter(item -> !id.equals(item.getId()))
                .collect(Collectors.toMap(CourseCategoryTreeDto::getId, Function.identity()));

        //最终返回的list
        List<CourseCategoryTreeDto> result = new ArrayList<>();
        // 依次遍历每个元素,排除根节点
        courseCategoryTreeDtos.stream().filter(item -> !id.equals(item.getId())).forEach(item -> {
            if(item.getParentid().equals(id)){
                result.add(item);
            }
            //找到当前节点的父节点
            CourseCategoryTreeDto parent = courseCategoryTreeDtoMap.get(item.getParentid());
            //如果父节点不为空,将当前节点放入父节点的children中
            if (parent != null) {
                if (parent.getChildrenTreeNodes() == null) {
                    parent.setChildrenTreeNodes(new ArrayList<CourseCategoryTreeDto>());
                }
                // 下边开始往ChildrenTreeNodes属性中放子节点
                parent.getChildrenTreeNodes().add(item);
            }
        });
        return result;
    }
}
