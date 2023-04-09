package com.goodstudy.media.controller;

import com.goodstudy.base.model.RestResponse;
import com.goodstudy.media.model.dto.UploadFileParamsDto;
import com.goodstudy.media.service.MediaFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * Description: 大文件上传接口
 *
 * @Author: Jack
 * Date: 2023/04/06 22:14
 * Version: 1.0
 */
@Api(tags = "大文件上传接口")
@RestController
public class BigFilesController {

    @Autowired
    private MediaFileService mediaFileService;

    /**
     * 文件上传前检查文件
     *
     * @param
     * @return RestResponse<Boolean>
     * @author Jack
     * @date 2023/4/6 22:15
     * @update_by Jack
     * @update_at 2023/4/6 22:15
     * @creed Talk is cheap, show me the comment !!!
     */
    @ApiOperation(value = "文件上传前检查文件", notes = "文件上传前检查文件")
    @PostMapping("/upload/checkfile")
    public RestResponse<Boolean> checkfile(@RequestParam("fileMd5") String fileMd5) throws Exception {
        return mediaFileService.checkFile(fileMd5);
    }

    /**
     * 分块文件上传前的检测
     * 请求媒资服务检查文件是否存在
     *
     * @param
     * @return com.goodstudy.base.model.RestResponse<java.lang.Boolean>
     * @author Jack
     * @date 2023/4/6 22:23
     * @update_by Jack
     * @update_at 2023/4/6 22:23
     * @creed Talk is cheap, show me the comment !!!
     */
    @ApiOperation(value = "分块文件上传前的检测", notes = "分块文件上传前的检测")
    @PostMapping("/upload/checkchunk")
    public RestResponse<Boolean> checkChunk(@RequestParam("fileMd5") String fileMd5,
                                            @RequestParam("chunk") int chunk) throws Exception {
        return mediaFileService.checkChunk(fileMd5, chunk);
    }

    /**
     * 分块文件上传
     *
     * @param file    org.springframework.web.multipart.MultipartFile
     * @param fileMd5 java.lang.String
     * @param chunk   int
     * @return com.goodstudy.base.model.RestResponse
     * @author Jack
     * @date 2023/4/6 22:25
     * @update_by Jack
     * @update_at 2023/4/6 22:25
     * @creed Talk is cheap, show me the comment !!!
     */
    @ApiOperation(value = "分块文件上传", notes = "分块文件上传")
    @PostMapping("/upload/uploadchunk")
    public RestResponse uploadchunk(@RequestParam("file") MultipartFile file,
                                    @RequestParam("fileMd5") String fileMd5,
                                    @RequestParam("chunk") int chunk) throws Exception {
        // 创建临时文件
        File tempFile = File.createTempFile("minio", "temp");
        // 上传的文件拷贝到临时文件
        file.transferTo(tempFile);
        // 获取临时文件的绝对路径
        String absolutePath = tempFile.getAbsolutePath();
        return mediaFileService.uploadChunk(fileMd5, chunk, absolutePath);
    }

    /**
     * 合并文件
     *
     * @param
     * @return com.goodstudy.base.model.RestResponse
     * @author Jack
     * @date 2023/4/6 22:26
     * @update_by Jack
     * @update_at 2023/4/6 22:26
     * @creed Talk is cheap, show me the comment !!!
     */
    @ApiOperation(value = "合并文件", notes = "合并文件")
    @PostMapping("/upload/mergechunks")
    public RestResponse mergeChunks(@RequestParam("fileMd5") String fileMd5,
                                    @RequestParam("fileName") String fileName,
                                    @RequestParam("chunkTotal") int chunkTotal) throws Exception {
        Long companyId = 1232141425L;
        UploadFileParamsDto uploadFileParamsDto = new UploadFileParamsDto();
        uploadFileParamsDto.setFileType("001002");
        uploadFileParamsDto.setTags("课程视频");
        uploadFileParamsDto.setRemark("");
        uploadFileParamsDto.setFilename(fileName);
        return mediaFileService.mergeChunk(companyId,fileMd5, fileName, chunkTotal, uploadFileParamsDto);
    }


}
