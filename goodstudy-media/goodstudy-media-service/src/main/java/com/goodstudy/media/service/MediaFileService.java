package com.goodstudy.media.service;

import com.goodstudy.base.model.PageParams;
import com.goodstudy.base.model.PageResult;
import com.goodstudy.base.model.RestResponse;
import com.goodstudy.media.model.dto.QueryMediaParamsDto;
import com.goodstudy.media.model.dto.UploadFileParamsDto;
import com.goodstudy.media.model.dto.UploadFileResultDto;
import com.goodstudy.media.model.po.MediaFiles;

import java.io.File;

/**
 * @author Mr.M
 * @version 1.0
 * @description 媒资文件管理业务类
 * @date 2022/9/10 8:55
 */
public interface MediaFileService {

    /**
     * 获得媒资文件
     *
     * @param mediaId java.lang.String
     * @return com.goodstudy.media.model.po.MediaFiles
     * @author Jack
     * @date 2023/4/12 20:36
     * @update_by Jack
     * @update_at 2023/4/12 20:36
     * @creed Talk is cheap, show me the comment !!!
     */
    MediaFiles getFileById(String mediaId);

    /**
     * @param pageParams          分页参数
     * @param queryMediaParamsDto 查询条件
     * @return com.xuecheng.base.model.PageResult<com.xuecheng.media.model.po.MediaFiles>
     * @description 媒资文件查询方法
     * @author Mr.M
     * @date 2022/9/10 8:57
     */
    PageResult<MediaFiles> queryMediaFiels(Long companyId, PageParams pageParams, QueryMediaParamsDto queryMediaParamsDto);

    /**
     * 上传文件
     *
     * @param companyId           java.lang.Long 企业id
     * @param uploadFileParamsDto com.goodstudy.media.model.dto.UploadFileParamsDto 上传文件参数
     * @param localFilePath       java.lang.String 本地文件路径
     * @return com.goodstudy.media.model.dto.UploadFileResultDto 上传文件结果
     * @author Jack
     * @date 2023/4/3 23:22
     * @update_by Jack
     * @update_at 2023/4/3 23:22
     * @creed Talk is cheap, show me the comment !!!
     */
    UploadFileResultDto uploadFile(Long companyId, UploadFileParamsDto uploadFileParamsDto, String localFilePath);

    /**
     * 上传文件数据库操作
     *
     * @param companyId           java.lang.Long
     * @param fileMd5             java.lang.String
     * @param uploadFileParamsDto com.goodstudy.media.model.dto.UploadFileParamsDto
     * @param bucket              java.lang.String
     * @param objectName          java.lang.String
     * @return com.goodstudy.media.model.po.MediaFiles
     * @author Jack
     * @date 2023/4/4 19:14
     * @update_by Jack
     * @update_at 2023/4/4 19:14
     * @creed Talk is cheap, show me the comment !!!
     */
    MediaFiles addMediaFilesToDb(Long companyId, String fileMd5, UploadFileParamsDto uploadFileParamsDto, String bucket, String objectName);


    /**
     * 将文件上传到minio
     *
     * @param localFilePath 文件本地路径
     * @param mimeType      媒体类型
     * @param bucket        桶
     * @param objectName    对象名
     * @return
     */
    boolean addMediaFilesToMinIO(String localFilePath, String mimeType, String bucket, String objectName);

    /**
     * 下载文件
     *
     * @param bucket     java.lang.String
     * @param objectName java.lang.String
     * @return java.io.File
     * @author Jack
     * @date 2023/4/9 17:26
     * @update_by Jack
     * @update_at 2023/4/9 17:26
     * @creed Talk is cheap, show me the comment !!!
     */
    File downloadFileFromMinIO(String bucket, String objectName);

    /**
     * 文件上传前检查文件 检查文件是否存在
     *
     * @param fileMd5 java.lang.String
     * @return com.goodstudy.base.model.RestResponse<java.lang.Boolean>
     * @author Jack
     * @date 2023/4/6 22:33
     * @update_by Jack
     * @update_at 2023/4/6 22:33
     * @creed Talk is cheap, show me the comment !!!
     */
    RestResponse<Boolean> checkFile(String fileMd5);

    /**
     * 文件上传前检查文件 检查文件是否存在
     *
     * @param fileMd5 java.lang.String
     * @param chunk   int
     * @return com.goodstudy.base.model.RestResponse<java.lang.Boolean>
     * @author Jack
     * @date 2023/4/6 22:33
     * @update_by Jack
     * @update_at 2023/4/6 22:33
     * @creed Talk is cheap, show me the comment !!!
     */
    RestResponse<Boolean> checkChunk(String fileMd5, int chunk);

    /**
     * 上传分块文件
     *
     * @param fileMd5            java.lang.String 文件md5
     * @param chunk              int 分块序号
     * @param localChunkFilePath java.lang.String 本地分块文件路径
     * @return com.goodstudy.base.model.RestResponse
     * @author Jack
     * @date 2023/4/9 11:16
     * @update_by Jack
     * @update_at 2023/4/9 11:16
     * @creed Talk is cheap, show me the comment !!!
     */
    RestResponse uploadChunk(String fileMd5, int chunk, String localChunkFilePath);


    /**
     * 合并分块文件
     *
     * @param companyId           java.lang.Long
     * @param fileMd5             java.lang.String
     * @param fileName            java.lang.String
     * @param chunkTotal          int 分块总数
     * @param uploadFileParamsDto com.goodstudy.media.model.dto.UploadFileParamsDto 上传文件参数
     * @return com.goodstudy.base.model.RestResponse
     * @author Jack
     * @date 2023/4/6 22:38
     * @update_by Jack
     * @update_at 2023/4/6 22:38
     * @creed Talk is cheap, show me the comment !!!
     */
    RestResponse mergeChunk(Long companyId, String fileMd5, String fileName, int chunkTotal, UploadFileParamsDto uploadFileParamsDto);

}
