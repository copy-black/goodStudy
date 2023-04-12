package com.goodstudy.media.controller;

import com.goodstudy.base.exception.GoodStudyException;
import com.goodstudy.base.model.RestResponse;
import com.goodstudy.media.model.po.MediaFiles;
import com.goodstudy.media.service.MediaFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description: 媒资公开控制器
 *
 * @Author: Jack
 * Date: 2023/04/12 20:30
 * Version: 1.0
 */
@Api(value = "媒资文件管理接口", tags = "媒资文件管理接口")
@RestController
@RequestMapping("/open")
public class MediaOpenController {

    @Autowired
    private MediaFileService mediaFileService;

    /**
     * Description: 预览媒资文件
     *
     * @param mediaId java.lang.String
     * @return com.goodstudy.base.model.RestResponse<java.lang.String>
     * @author Jack
     * @date 2023/4/12 20:33
     * @update_by Jack
     * @update_at 2023/4/12 20:33
     * @creed Talk is cheap, show me the comment !!!
     */
    @ApiOperation("预览媒资文件")
    @GetMapping("/preview/{mediaId}")
    public RestResponse<String> getPlayUrlByMediaId(@PathVariable String mediaId) {
        MediaFiles mediaFiles = mediaFileService.getFileById(mediaId);
        if(mediaFiles == null || StringUtils.isEmpty(mediaFiles.getUrl())){
            GoodStudyException.cast("视频还没有转码处理");
        }
        return RestResponse.success(mediaFiles.getUrl());
    }


}
