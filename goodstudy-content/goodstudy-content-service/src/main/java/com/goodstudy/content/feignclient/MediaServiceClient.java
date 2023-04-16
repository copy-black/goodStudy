package com.goodstudy.content.feignclient;

import com.goodstudy.content.config.MultipartSupportConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author Mr.M
 * @version 1.0
 * @description 远程调用媒资服务的接口
 * @date 2023/2/22 10:26
 */
@FeignClient(value = "goodstudy-media-api", configuration = {MultipartSupportConfig.class}, fallback = MediaServiceClientFallbackFactory.class)
public interface MediaServiceClient {

    @RequestMapping(value = "/media/upload/coursefile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String upload(@RequestPart("filedata") MultipartFile filedata,
                         @RequestParam(value = "objectName", required = false) String objectName) throws IOException;

}
