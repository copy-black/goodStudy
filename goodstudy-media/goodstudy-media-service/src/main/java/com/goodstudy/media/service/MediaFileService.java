package com.goodstudy.media.service;

import com.goodstudy.base.model.PageParams;
import com.goodstudy.base.model.PageResult;
import com.goodstudy.media.model.dto.QueryMediaParamsDto;
import com.goodstudy.media.model.dto.UploadFileParamsDto;
import com.goodstudy.media.model.dto.UploadFileResultDto;
import com.goodstudy.media.model.po.MediaFiles;

/**
 * @author Mr.M
 * @version 1.0
 * @description 媒资文件管理业务类
 * @date 2022/9/10 8:55
 */
public interface MediaFileService {

    /**
     * @param pageParams          分页参数
     * @param queryMediaParamsDto 查询条件
     * @return com.xuecheng.base.model.PageResult<com.xuecheng.media.model.po.MediaFiles>
     * @description 媒资文件查询方法
     * @author Mr.M
     * @date 2022/9/10 8:57
     */
    public PageResult<MediaFiles> queryMediaFiels(Long companyId, PageParams pageParams, QueryMediaParamsDto queryMediaParamsDto);

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
    public MediaFiles addMediaFilesToDb(Long companyId, String fileMd5, UploadFileParamsDto uploadFileParamsDto, String bucket, String objectName);
}
