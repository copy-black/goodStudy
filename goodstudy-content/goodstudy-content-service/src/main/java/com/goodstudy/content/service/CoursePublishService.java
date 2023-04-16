package com.goodstudy.content.service;

import com.goodstudy.content.model.dto.CoursePreviewDto;

import java.io.File;

/**
 * Description: 课程预览、发布接口
 *
 * @Author: Jack
 * Date: 2023/04/12 20:00
 * Version: 1.0
 */
public interface CoursePublishService {

    /**
     * Description: 获取课程预览信息
     *
     * @param courseId java.lang.Long
     * @return com.goodstudy.content.model.dto.CoursePreviewDto
     * @author Jack
     * @date 2023/4/12 20:00
     * @update_by Jack
     * @update_at 2023/4/12 20:00
     * @creed Talk is cheap, show me the comment !!!
     */
    CoursePreviewDto getCoursePreviewInfo(Long courseId);

    /**
     * Description: 课程提交审核
     *
     * @param companyId java.lang.Long
     * @param courseId  java.lang.Long
     * @return void
     * @author Jack
     * @date 2023/4/13 19:55
     * @update_by Jack
     * @update_at 2023/4/13 19:55
     * @creed Talk is cheap, show me the comment !!!
     */
    void commitAudit(Long companyId, Long courseId);

    /**
     * Description: 课程发布
     *
     * @param companyId java.lang.Long
     * @param courseId  java.lang.Long
     * @return void
     * @author Jack
     * @date 2023/4/13 20:43
     * @update_by Jack
     * @update_at 2023/4/13 20:43
     * @creed Talk is cheap, show me the comment !!!
     */
    void publish(Long companyId, Long courseId);


    /**
     * 课程静态化
     *
     * @param courseId java.lang.Long
     * @return java.io.File
     * @author Jack
     * @date 2023/4/16 11:16
     * @update_by Jack
     * @update_at 2023/4/16 11:16
     * @creed Talk is cheap, show me the comment !!!
     */
    File generateCourseHtml(Long courseId);

    /**
     * 上传课程静态化文件
     *
     * @param courseId java.lang.Long
     * @param file     java.io.File
     * @return void
     * @author Jack
     * @date 2023/4/16 11:16
     * @update_by Jack
     * @update_at 2023/4/16 11:16
     * @creed Talk is cheap, show me the comment !!!
     */
    void uploadCourseHtml(Long courseId, File file);
}
