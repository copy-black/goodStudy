package com.goodstudy.media.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.goodstudy.base.exception.GoodStudyException;
import com.goodstudy.base.model.PageParams;
import com.goodstudy.base.model.PageResult;
import com.goodstudy.base.model.RestResponse;
import com.goodstudy.media.mapper.MediaFilesMapper;
import com.goodstudy.media.mapper.MediaProcessMapper;
import com.goodstudy.media.model.dto.UploadFileParamsDto;
import com.goodstudy.media.model.dto.UploadFileResultDto;
import com.goodstudy.media.model.po.MediaProcess;
import com.goodstudy.media.service.MediaFileService;
import com.goodstudy.media.model.dto.QueryMediaParamsDto;
import com.goodstudy.media.model.po.MediaFiles;
import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import io.minio.*;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Mr.M
 * @version 1.0
 * @description 媒资文件管理接口实现类
 * @date 2022/9/10 8:58
 */
@Slf4j
@Service
public class MediaFileServiceImpl implements MediaFileService {

    @Autowired
    private MinioClient minioClient;

    /**
     * minio文件存储桶名称
     */
    @Value("${minio.bucket.files}")
    private String bucketFiles;

    @Value("${minio.bucket.videofiles}")
    private String bucketVideoFiles;

    @Autowired
    MediaFileService currentProxy;

    @Autowired
    MediaFilesMapper mediaFilesMapper;

    @Autowired
    MediaProcessMapper mediaProcessMapper;

    @Override
    public MediaFiles getFileById(String mediaId) {
        MediaFiles mediaFiles = mediaFilesMapper.selectById(mediaId);
        return mediaFiles;
    }

    @Override
    public PageResult<MediaFiles> queryMediaFiels(Long companyId, PageParams pageParams, QueryMediaParamsDto queryMediaParamsDto) {
        //构建查询条件对象
        LambdaQueryWrapper<MediaFiles> queryWrapper = new LambdaQueryWrapper<>();
        //分页对象
        Page<MediaFiles> page = new Page<>(pageParams.getPageNo(), pageParams.getPageSize());
        // 查询数据内容获得结果
        Page<MediaFiles> pageResult = mediaFilesMapper.selectPage(page, queryWrapper);
        // 获取数据列表
        List<MediaFiles> list = pageResult.getRecords();
        // 获取数据总数
        long total = pageResult.getTotal();
        // 构建结果集
        PageResult<MediaFiles> mediaListResult = new PageResult<>(list, total, pageParams.getPageNo(), pageParams.getPageSize());
        return mediaListResult;

    }

    /**
     * 上传文件
     *
     * @param companyId           java.lang.Long
     * @param uploadFileParamsDto com.goodstudy.media.model.dto.UploadFileParamsDto
     * @param localFilePath       java.lang.String
     * @return com.goodstudy.media.model.dto.UploadFileResultDto
     * @author Jack
     * @date 2023/4/4 16:08
     * @update_by Jack
     * @update_at 2023/4/4 16:08
     * @creed Talk is cheap, show me the comment !!!
     */
    @Override
    public UploadFileResultDto uploadFile(Long companyId, UploadFileParamsDto uploadFileParamsDto, String localFilePath, String objectName) {
        File file = new File(localFilePath);
        if (!file.exists()) {
            GoodStudyException.cast("文件不存在");
        }
        // 文件名称
        String fileName = uploadFileParamsDto.getFilename();
        // 文件扩展名
        String extension = fileName.substring(fileName.lastIndexOf("."));
        // 文件mimeType
        String mimeType = getMimeType(extension);
        // 文件md5
        String fileMd5 = getFileMd5(file);
        // 文件的默认目录
        String defaultFolderPath = getDefaultFolderPath();
        // 存储到minio中的对象名(带目录)
        if(StringUtils.isEmpty(objectName)){
            objectName =  defaultFolderPath + fileMd5 + extension;
        }
        //String objectName = defaultFolderPath + fileMd5 + extension;
        boolean b = addMediaFilesToMinIO(localFilePath, mimeType, bucketFiles, objectName);
        if (!b) {
            GoodStudyException.cast("上传文件到minio失败");
        }
        // 文件大小
        long fileSize = file.length();
        uploadFileParamsDto.setFileSize(fileSize);
        // 将文件信息存储到数据库
        MediaFiles mediaFiles = currentProxy.addMediaFilesToDb(companyId, fileMd5, uploadFileParamsDto, bucketFiles, objectName);
        // 返回结果
        UploadFileResultDto uploadFileResultDto = new UploadFileResultDto();
        BeanUtils.copyProperties(mediaFiles, uploadFileResultDto);
        return uploadFileResultDto;
    }

    /**
     * 上传文件到minio
     *
     * @param localFilePath java.lang.String
     * @param mimeType      java.lang.String
     * @param bucket        java.lang.String
     * @param objectName    java.lang.String
     * @return boolean
     * @author Jack
     * @date 2023/4/4 15:13
     * @update_by Jack
     * @update_at 2023/4/4 15:13
     * @creed Talk is cheap, show me the comment !!!
     */
    public boolean addMediaFilesToMinIO(String localFilePath, String mimeType, String bucket, String objectName) {
        try {
            UploadObjectArgs testbucket = UploadObjectArgs.builder()
                    .bucket(bucket)
                    .object(objectName)
                    .filename(localFilePath)
                    .contentType(mimeType)
                    .build();
            minioClient.uploadObject(testbucket);
            log.debug("上传文件到minio成功,bucket:{},objectName:{}", bucket, objectName);
            System.out.println("上传成功");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("上传文件到minio出错,bucket:{},objectName:{},错误原因:{}", bucket, objectName, e.getMessage(), e);
            return false;
        }
    }


    /**
     * 上传文件到数据库
     *
     * @param
     * @return com.goodstudy.media.model.po.MediaFiles
     * @author Jack
     * @date 2023/4/4 15:19
     * @update_by Jack
     * @update_at 2023/4/4 15:19
     * @creed Talk is cheap, show me the comment !!!
     */
    @Transactional
    public MediaFiles addMediaFilesToDb(Long companyId, String fileMd5, UploadFileParamsDto uploadFileParamsDto, String bucket, String objectName) {
        // 从数据库查询文件
        MediaFiles mediaFiles = mediaFilesMapper.selectById(fileMd5);
        if (mediaFiles == null) {
            // 不存在则添加
            mediaFiles = new MediaFiles();
            BeanUtils.copyProperties(uploadFileParamsDto, mediaFiles);
            mediaFiles.setId(fileMd5);
            mediaFiles.setFileId(fileMd5);
            mediaFiles.setCompanyId(companyId);
            mediaFiles.setUrl("/" + bucket + "/" + objectName);
            mediaFiles.setBucket(bucket);
            mediaFiles.setFilePath(objectName);
            mediaFiles.setCreateDate(LocalDateTime.now());
            mediaFiles.setAuditStatus("002003");
            mediaFiles.setStatus("1");
            //保存文件信息到文件表
            int insert = mediaFilesMapper.insert(mediaFiles);
            if (insert < 0) {
                log.error("保存文件信息到数据库失败,{}", mediaFiles.toString());
                GoodStudyException.cast("保存文件信息失败");
            }
            // 添加到待处理任务表
            addWaitingTask(mediaFiles);
            log.debug("保存文件信息到数据库成功,{}", mediaFiles.toString());
        }
        return mediaFiles;
    }

    @Override
    public RestResponse<Boolean> checkFile(String fileMd5) {
        // 从数据库查询文件
        MediaFiles mediaFiles = mediaFilesMapper.selectById(fileMd5);
        if (mediaFiles != null) {
            // 桶
            String bucket = mediaFiles.getBucket();
            // 文件路径
            String filePath = mediaFiles.getFilePath();
            // 文件流
            InputStream inputStream = null;
            try {
                // 获取文件流
                inputStream = minioClient.getObject(GetObjectArgs.builder().bucket(bucket).object(filePath).build());
                if (inputStream != null) {
                    return RestResponse.success(true);
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error("获取文件流出错,错误原因:{}", e.getMessage(), e);
            }
        }
        // 文件不存在
        return RestResponse.success(false);
    }

    @Override
    public RestResponse<Boolean> checkChunk(String fileMd5, int chunk) {
        // 得到分块文件目录
        String chunkFileFolderPath = getChunkFileFolderPath(fileMd5);
        // 得到分块文件的路径
        String chunkFilePath = chunkFileFolderPath + chunk;
        // 文件流
        InputStream inputStream = null;
        try {
            // 获取文件流
            inputStream = minioClient.getObject(GetObjectArgs.builder().bucket(bucketVideoFiles).object(chunkFilePath).build());
            if (inputStream != null) {
                return RestResponse.success(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取文件流出错,错误原因:{}", e.getMessage(), e);
        }
        // 分块文件不存在
        return RestResponse.success(false);
    }

    @Override
    public RestResponse uploadChunk(String fileMd5, int chunk, String localChunkFilePath) {
        // 得到分块文件的目录路径
        String chunkFileFolderPath = getChunkFileFolderPath(fileMd5);
        // 得到分块文件的路径
        String chunkFilePath = chunkFileFolderPath + chunk;
        // mimeType
        String mimeType = getMimeType(null);

        // 将文件存储至minIO
        boolean b = addMediaFilesToMinIO(localChunkFilePath, mimeType, bucketVideoFiles, chunkFilePath);
        if (!b) {
            log.debug("上传分块文件失败:{}", chunkFilePath);
            return RestResponse.validfail(false, "上传分块文件失败");
        }
        return RestResponse.success(true);
    }

    @Override
    public RestResponse mergeChunk(Long companyId, String fileMd5, String fileName, int chunkTotal, UploadFileParamsDto uploadFileParamsDto) {
        // =====获取分块文件路径=====
        String chunkFileFolderPath = getChunkFileFolderPath(fileMd5);

        // 组成将分块文件路径组成 List<ComposeSource>
        List<ComposeSource> sourceObjectList = Stream.iterate(0, i -> ++i)
                .limit(chunkTotal)
                .map(i -> ComposeSource.builder()
                        .bucket(bucketVideoFiles)
                        .object(chunkFileFolderPath.concat(Integer.toString(i)))
                        .build())
                .collect(Collectors.toList());
        // =====合并=====
        // 文件名称
        String filename = uploadFileParamsDto.getFilename();
        // 文件扩展名
        String fileExt = filename.substring(filename.lastIndexOf("."));
        // 合并文件路径
        String mergeFilePath = getFilePathByMd5(fileMd5, fileExt);
        try {
            //合并文件
            ObjectWriteResponse response = minioClient.composeObject(
                    ComposeObjectArgs.builder()
                            .bucket(bucketVideoFiles)
                            .object(mergeFilePath)
                            .sources(sourceObjectList)
                            .build());
            log.debug("合并文件成功:{}", mergeFilePath);
        } catch (Exception e) {
            log.debug("合并文件失败,fileMd5:{},异常:{}", fileMd5, e.getMessage(), e);
            return RestResponse.validfail(false, "合并文件失败。");
        }

        // ====验证md5====
        // 下载合并后的文件
        File minioFile = downloadFileFromMinIO(bucketVideoFiles, mergeFilePath);
        if (minioFile == null) {
            log.debug("下载合并后文件失败,mergeFilePath:{}", mergeFilePath);
            return RestResponse.validfail(false, "下载合并后文件失败。");
        }

        try (InputStream newFileInputStream = new FileInputStream(minioFile)) {
            // minio上文件的md5值
            String md5Hex = DigestUtils.md5Hex(newFileInputStream);
            //比较md5值，不一致则说明文件不完整
            if (!fileMd5.equals(md5Hex)) {
                return RestResponse.validfail(false, "文件合并校验失败，最终上传失败。");
            }
            // 文件大小
            uploadFileParamsDto.setFileSize(minioFile.length());
        } catch (Exception e) {
            log.debug("校验文件失败,fileMd5:{},异常:{}", fileMd5, e.getMessage(), e);
            return RestResponse.validfail(false, "文件合并校验失败，最终上传失败。");
        } finally {
            if (minioFile != null) {
                minioFile.delete();
            }
        }
        // 文件入库
        currentProxy.addMediaFilesToDb(companyId, fileMd5, uploadFileParamsDto, bucketVideoFiles, mergeFilePath);
        //=====清除分块文件=====
        clearChunkFiles(chunkFileFolderPath, chunkTotal);
        return RestResponse.success(true);
    }

    /**
     * 添加到待处理任务表
     * @param media
     */
    private void addWaitingTask(MediaFiles media){
        // 文件名称
        String filename = media.getFilename();
        // 文件扩展名
        String fileExt = filename.substring(filename.lastIndexOf("."));
        // 文件mimeType
        String mimeType = getMimeType(fileExt);
        // 如果是avi格式的视频文件，添加到待处理任务表
        if (mimeType.equals("video/x-msvideo")) {
            MediaProcess mediaProcess = new MediaProcess();
            BeanUtils.copyProperties(media,mediaProcess);
            // 未处理
            mediaProcess.setStatus("1");
            // 失败次数默认为0
            mediaProcess.setFailCount(0);
            mediaProcessMapper.insert(mediaProcess);
        }

    }

    /**
     * 从minio下载文件
     *
     * @param bucket
     * @param objectName
     * @return
     */
    public File downloadFileFromMinIO(String bucket, String objectName) {
        //临时文件
        File minioFile = null;
        FileOutputStream outputStream = null;
        try {
            InputStream stream = minioClient.getObject(GetObjectArgs.builder()
                    .bucket(bucket)
                    .object(objectName)
                    .build());
            //创建临时文件
            minioFile = File.createTempFile("minio", ".merge");
            outputStream = new FileOutputStream(minioFile);
            IOUtils.copy(stream, outputStream);
            return minioFile;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 得到合并后的文件的地址
     *
     * @param fileMd5
     * @param fileExt
     * @return
     */
    private String getFilePathByMd5(String fileMd5, String fileExt) {
        return fileMd5.substring(0, 1) + "/" + fileMd5.substring(1, 2) + "/" + fileMd5 + "/" + fileMd5 + fileExt;
    }

    /**
     * 清除分块文件
     *
     * @param chunkFileFolderPath
     * @param chunkTotal
     */
    private void clearChunkFiles(String chunkFileFolderPath, int chunkTotal) {
        try {
            List<DeleteObject> deleteObjects = Stream.iterate(0, i -> ++i)
                    .limit(chunkTotal)
                    .map(i -> new DeleteObject(chunkFileFolderPath.concat(Integer.toString(i))))
                    .collect(Collectors.toList());

            RemoveObjectsArgs removeObjectsArgs = RemoveObjectsArgs.builder().bucket("video").objects(deleteObjects).build();
            Iterable<Result<DeleteError>> results = minioClient.removeObjects(removeObjectsArgs);
            results.forEach(r -> {
                DeleteError deleteError = null;
                try {
                    deleteError = r.get();
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("清楚分块文件失败,objectname:{}", deleteError.objectName(), e);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            log.error("清楚分块文件失败,chunkFileFolderPath:{}", chunkFileFolderPath, e);
        }
    }

    /**
     * 获取分块文件所在目录
     *
     * @param fileMd5
     * @return
     */
    private String getChunkFileFolderPath(String fileMd5) {
        return fileMd5.substring(0, 1) + "/" + fileMd5.substring(1, 2) + "/" + fileMd5 + "/" + "chunk" + "/";
    }

    /**
     * 获取默认的文件夹路径 年/月/日
     *
     * @return
     */
    private String getDefaultFolderPath() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 当前时间所对应的文件夹名称
        String folder = sdf.format(new Date()).replace("-", "/") + "/";
        return folder;
    }

    /**
     * 获取文件的md5值
     *
     * @param File
     * @return
     */
    private String getFileMd5(File File) {
        try (FileInputStream fileInputStream = new FileInputStream(File)) {
            String fileMd5 = DigestUtils.md5Hex(fileInputStream);
            return fileMd5;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    /**
     * 根据扩展名获取mimeType
     *
     * @param extension
     * @return
     */
    private String getMimeType(String extension) {
        if (extension == null) {
            extension = "";
        }
        // 根据扩展名取出mimeType
        ContentInfo extensionMatch = ContentInfoUtil.findExtensionMatch(extension);
        // 默认值
        String mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        if (extensionMatch != null) {
            mimeType = extensionMatch.getMimeType();
        }
        return mimeType;
    }
}
