package com.goodstudy.media;

import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import io.minio.*;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.io.FileOutputStream;
import java.io.FilterInputStream;

/**
 * Description: 测试Minio
 *
 * @Author: Jack
 * Date: 2023/04/03 22:22
 * Version: 1.0
 */
public class MinioTest {

    static MinioClient minioClient = MinioClient.builder()
            .endpoint("http://192.168.101.65:9000")
            .credentials("minioadmin", "minioadmin")
            .build();


    @Test
    public void testMinio() {
        try {
            UploadObjectArgs uploadObjectArgs = UploadObjectArgs.builder()
                    .bucket("testbucket")
                    .object("001/test.mp4")
                    .filename("C:\\Users\\18488\\Videos\\Captures\\Horizon.mp4")
                    .contentType("video/mp4")
                    .build();
            minioClient.uploadObject(uploadObjectArgs);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("上传成功");
        }
    }

    @Test
    public void testUpload() {
        // 根据扩展名取出mimeType
        ContentInfo extensionMatch = ContentInfoUtil.findExtensionMatch(".mp4");
        String mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;

        if (extensionMatch != null) {
            mimeType = extensionMatch.getMimeType();
        }
        try {
            UploadObjectArgs uploadObjectArgs = UploadObjectArgs.builder()
                    .bucket("testbucket")
                    .object("001/test.mp4")
                    .filename("C:\\Users\\18488\\Videos\\Captures\\Horizon.mp4")
                    .contentType(mimeType)
                    .build();
            minioClient.uploadObject(uploadObjectArgs);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("上传成功");
        }
    }

    @Test
    public void testDelete() {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket("testbucket")
                    .object("001/test.mp4")
                    .build());
            System.out.println("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("删除失败");
        }
    }

    @Test
    public void getFiles() {
        // 获取文件
        GetObjectArgs testbucket = GetObjectArgs.builder()
                .bucket("testbucket")
                .object("QQ.png")
                .build();
        try {
            // 获取文件
            FilterInputStream inputStream = minioClient.getObject(testbucket);
            // 保存文件
            FileOutputStream outputStream = new FileOutputStream("C:\\QQ.png");
            IOUtils.copy(inputStream, outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
