package com.goodstudy.media.controller;

import com.goodstudy.base.model.PageParams;
import com.goodstudy.base.model.PageResult;
import com.goodstudy.media.model.dto.QueryMediaParamsDto;
import com.goodstudy.media.model.dto.UploadFileParamsDto;
import com.goodstudy.media.model.dto.UploadFileResultDto;
import com.goodstudy.media.model.po.MediaFiles;
import com.goodstudy.media.service.MediaFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author Mr.M
 * @version 1.0
 * @description 媒资文件管理接口
 * @date 2022/9/6 11:29
 */
@Api(value = "媒资文件管理接口", tags = "媒资文件管理接口")
@RestController
public class MediaFilesController {

    @Autowired
    MediaFileService mediaFileService;

    @ApiOperation("媒资列表查询接口")
    @PostMapping("/files")
    public PageResult<MediaFiles> list(PageParams pageParams, @RequestBody QueryMediaParamsDto queryMediaParamsDto) {
        Long companyId = 1232141425L;
        return mediaFileService.queryMediaFiels(companyId, pageParams, queryMediaParamsDto);
    }

    /**
     * 上传文件
     *
     * @param file org.springframework.web.multipart.MultipartFile
     * @return com.goodstudy.media.model.dto.UploadFileResultDto
     * @author Jack
     * @date 2023/4/3 23:18
     * @update_by Jack
     * @update_at 2023/4/3 23:18
     * @creed Talk is cheap, show me the comment !!!
     */
    @ApiOperation("上传文件")
    @PostMapping(value = "/upload/coursefile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public UploadFileResultDto upload(@RequestPart("filedata") MultipartFile file,
                                      @RequestParam(value= "objectName",required=false) String objectName) throws IOException {
        Long companyId = 1232141425L;
        // 上传文件参数uploadFileParamsDto
        UploadFileParamsDto uploadFileParamsDto = new UploadFileParamsDto();
        // 文件大小
        uploadFileParamsDto.setFileSize(file.getSize());
        // 图片
        uploadFileParamsDto.setFileType("001001");
        // 文件名称
        uploadFileParamsDto.setFilename(file.getOriginalFilename());
        // 文件大小
        long fileSize = file.getSize();
        uploadFileParamsDto.setFileSize(fileSize);
        // 创建临时文件
        File tempFile = File.createTempFile("minio", "temp");
        // 上传的文件拷贝到临时文件
        file.transferTo(tempFile);
        // 文件路径
        String absolutePath = tempFile.getAbsolutePath();
        // 上传文件
        UploadFileResultDto uploadFileResultDto = mediaFileService.uploadFile(companyId, uploadFileParamsDto, absolutePath, objectName);
        return uploadFileResultDto;
    }

}
