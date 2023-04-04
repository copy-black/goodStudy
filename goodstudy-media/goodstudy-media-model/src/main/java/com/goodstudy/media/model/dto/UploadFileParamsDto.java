package com.goodstudy.media.model.dto;

import lombok.Data;

/**
 * Description: 上传文件参数dto
 *
 * @Author: Jack
 * Date: 2023/04/03 23:17
 * Version: 1.0
 */
@Data
public class UploadFileParamsDto {

    /**
     * 文件名称
     */
    private String filename;
    /**
     * 文件类型（文档，音频，视频）
     */
    private String fileType;
    /**
     * 文件大小
     */
    private Long fileSize;

    /**
     * 标签
     */
    private String tags;

    /**
     * 上传人
     */
    private String username;

    /**
     * 备注
     */
    private String remark;
}
