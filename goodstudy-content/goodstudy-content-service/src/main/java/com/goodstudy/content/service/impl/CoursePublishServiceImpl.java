package com.goodstudy.content.service.impl;

import com.alibaba.fastjson.JSON;
import com.goodstudy.base.exception.CommonError;
import com.goodstudy.base.exception.GoodStudyException;
import com.goodstudy.content.config.MultipartSupportConfig;
import com.goodstudy.content.feignclient.MediaServiceClient;
import com.goodstudy.content.mapper.CourseBaseMapper;
import com.goodstudy.content.mapper.CourseMarketMapper;
import com.goodstudy.content.mapper.CoursePublishMapper;
import com.goodstudy.content.mapper.CoursePublishPreMapper;
import com.goodstudy.content.model.dto.CourseBaseInfoDto;
import com.goodstudy.content.model.dto.CoursePreviewDto;
import com.goodstudy.content.model.dto.TeachplanDto;
import com.goodstudy.content.model.po.CourseBase;
import com.goodstudy.content.model.po.CourseMarket;
import com.goodstudy.content.model.po.CoursePublish;
import com.goodstudy.content.model.po.CoursePublishPre;
import com.goodstudy.content.service.CourseBaseInfoService;
import com.goodstudy.content.service.CoursePublishService;
import com.goodstudy.content.service.TeachplanService;
import com.goodstudy.messagesdk.model.po.MqMessage;
import com.goodstudy.messagesdk.service.MqMessageService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 *
 * @Author: Jack
 * Date: 2023/04/12 20:01
 * Version: 1.0
 */
@Slf4j
@Service
public class CoursePublishServiceImpl implements CoursePublishService {

    @Autowired
    MqMessageService mqMessageService;

    @Autowired
    MediaServiceClient mediaServiceClient;

    @Autowired
    TeachplanService teachplanService;

    @Autowired
    CourseBaseInfoService courseBaseInfoService;

    @Autowired
    CourseBaseMapper courseBaseMapper;

    @Autowired
    CourseMarketMapper courseMarketMapper;

    @Autowired
    CoursePublishMapper coursePublishMapper;

    @Autowired
    CoursePublishPreMapper coursePublishPreMapper;

    @Override
    public CoursePreviewDto getCoursePreviewInfo(Long courseId) {
        //课程基本信息、营销信息
        CourseBaseInfoDto courseBaseInfo = courseBaseInfoService.getCourseBaseInfo(courseId);
        //课程计划信息
        List<TeachplanDto> teachplanTree = teachplanService.findTeachplanTree(courseId);
        CoursePreviewDto coursePreviewDto = new CoursePreviewDto();
        coursePreviewDto.setCourseBase(courseBaseInfo);
        coursePreviewDto.setTeachplans(teachplanTree);
        return coursePreviewDto;
    }

    @Override
    public void commitAudit(Long companyId, Long courseId) {
        // 查询课程基本信息
        CourseBase courseBase = courseBaseMapper.selectById(courseId);

        // 课程审核状态
        String auditStatus = courseBase.getAuditStatus();
        // 当前审核状态为已提交不允许再次提交
        if ("202003".equals(auditStatus)) {
            throw new RuntimeException("课程已提交审核，不允许重复提交");
        }
        // 本机构只允许提交本机构的课程
        if (!courseBase.getCompanyId().equals(companyId)) {
            throw new RuntimeException("只允许提交本机构的课程");
        }
        //3、没有上传图片不允许提交审核。
        if (StringUtils.isEmpty(courseBase.getPic())) {
            throw new RuntimeException("没有上传图片不允许提交审核");
        }

        CoursePublishPre coursePublishPre = new CoursePublishPre();
        CourseBaseInfoDto courseBaseInfo = courseBaseInfoService.getCourseBaseInfo(courseId);
        // 课程基本信息加部分营销信息
        BeanUtils.copyProperties(courseBaseInfo, coursePublishPre);
        // 课程营销信息
        CourseMarket courseMarket = courseMarketMapper.selectById(courseId);
        //转为json
        String courseMarketJson = JSON.toJSONString(courseMarket);
        coursePublishPre.setMarket(courseMarketJson);

        // 查询课程计划信息
        List<TeachplanDto> teachplanTree = teachplanService.findTeachplanTree(courseId);
        //没有添加课程计划不允许提交审核。
        if (teachplanTree.size() <= 0) {
            throw new RuntimeException("没有添加课程计划不允许提交审核");
        }
        // 转为json
        String teachplanTreeString = JSON.toJSONString(teachplanTree);
        coursePublishPre.setTeachplan(teachplanTreeString);

        // 课程状态为已提交
        coursePublishPre.setStatus("202003");
        // 教学机构id
        coursePublishPre.setCompanyId(companyId);
        //提交时间
        coursePublishPre.setCreateDate(LocalDateTime.now());

        CoursePublishPre coursePublishPreUpdate = coursePublishPreMapper.selectById(courseId);
        if (coursePublishPreUpdate == null) {
            coursePublishPreMapper.insert(coursePublishPre);
        } else {
            coursePublishPreMapper.updateById(coursePublishPre);
        }

        // 更新课程审核状态
        courseBase.setAuditStatus("202003");
        courseBaseMapper.updateById(courseBase);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void publish(Long companyId, Long courseId) {
        // 约束条件
        CoursePublishPre coursePublishPre = coursePublishPreMapper.selectById(courseId);
        if (coursePublishPre.getStatus() == null) {
            GoodStudyException.cast("课程未提交审核，不允许发布。");
        }
        if (!coursePublishPre.getCompanyId().equals(companyId)) {
            GoodStudyException.cast("不允许提交其它机构的课程。");
        }
        if (!"202004".equals(coursePublishPre.getStatus())) {
            GoodStudyException.cast("课程未审核通过，不允许发布。");
        }

        //保存课程发布信息
        saveCoursePublish(courseId);

        //保存消息表
        saveCoursePublishMessage(courseId);

        // 删除课程预发布信息
        coursePublishPreMapper.deleteById(courseId);
    }

    @Override
    public File generateCourseHtml(Long courseId) {
        //静态化文件
        File htmlFile  = null;

        try {
            //配置freemarker
            Configuration configuration = new Configuration(Configuration.getVersion());

            //加载模板
            //选指定模板路径,classpath下templates下
            //得到classpath路径
            String classpath = this.getClass().getResource("/").getPath();
            configuration.setDirectoryForTemplateLoading(new File(classpath + "/templates/"));
            //设置字符编码
            configuration.setDefaultEncoding("utf-8");

            //指定模板文件名称
            Template template = configuration.getTemplate("course_template.ftl");

            //准备数据
            CoursePreviewDto coursePreviewInfo = this.getCoursePreviewInfo(courseId);

            Map<String, Object> map = new HashMap<>();
            map.put("model", coursePreviewInfo);

            //静态化
            //参数1：模板，参数2：数据模型
            String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
//            System.out.println(content);
            //将静态化内容输出到文件中
            InputStream inputStream = IOUtils.toInputStream(content);
            //创建静态化文件
            htmlFile = File.createTempFile("course",".html");
            log.debug("课程静态化，生成静态文件:{}",htmlFile.getAbsolutePath());
            //输出流
            FileOutputStream outputStream = new FileOutputStream(htmlFile);
            IOUtils.copy(inputStream, outputStream);
        } catch (Exception e) {
            log.error("课程静态化异常:{}",e.toString());
            GoodStudyException.cast("课程静态化异常");
        }

        return htmlFile;
    }

    @Override
    public void uploadCourseHtml(Long courseId, File file) {
        MultipartFile multipartFile = MultipartSupportConfig.getMultipartFile(file);
        String course = null;
        try {
            course = mediaServiceClient.upload(multipartFile, "course/"+courseId+".html");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (course == null) {
            GoodStudyException.cast("课程静态化文件上传失败");
        }
    }

    /**
     * 保存消息表
     *
     * @param courseId
     */
    private void saveCoursePublish(Long courseId) {
        // 查询课程预发布表
        CoursePublishPre coursePublishPre = coursePublishPreMapper.selectById(courseId);
        if (coursePublishPre == null) {
            GoodStudyException.cast("课程预发布数据为空");
        }

        CoursePublish coursePublish = new CoursePublish();
        BeanUtils.copyProperties(coursePublishPre, coursePublish);
        coursePublishPre.setStatus("202002");
        CoursePublish coursePublishUpdate = coursePublishMapper.selectById(courseId);
        if (coursePublishUpdate == null) {
            coursePublishMapper.insert(coursePublish);
        } else {
            coursePublishMapper.updateById(coursePublish);
        }
        // 更新课程基本表的发布状态
        CourseBase courseBase = courseBaseMapper.selectById(courseId);
        courseBase.setStatus("202002");
        courseBaseMapper.updateById(courseBase);
    }

    private void saveCoursePublishMessage(Long courseId) {
        MqMessage mqMessage = mqMessageService.addMessage("course_publish", String.valueOf(courseId), null, null);
        if (mqMessage == null) {
            GoodStudyException.cast(CommonError.UNKOWN_ERROR);
        }
    }
}
