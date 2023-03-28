package com.goodstudy.content;

import com.goodstudy.content.model.dto.CourseCategoryTreeDto;
import com.goodstudy.content.service.CourseCategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * Description:
 *
 * @Author: Jack
 * Date: 2023/03/27 19:52
 * Version: 1.0
 */
@SpringBootTest
public class CourseCategoryServiceTests {

    @Autowired
    private CourseCategoryService courseCategoryService;

    @Test
    public void queryTreeNodes() {
        List<CourseCategoryTreeDto> courseCategoryTreeDtos = courseCategoryService.queryTreeNodes("1");
        // 循环遍历courseCategoryTreeDtos
        for (CourseCategoryTreeDto courseCategoryTreeDto : courseCategoryTreeDtos) {
            System.out.println(courseCategoryTreeDto);
        }
    }
}
