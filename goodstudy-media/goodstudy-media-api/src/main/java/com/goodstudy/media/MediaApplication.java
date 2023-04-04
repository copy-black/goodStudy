package com.goodstudy.media;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Description: 媒资服务启动类
 *
 * @Author: Jack
 * Date: 2023/03/25 18:19
 * Version: 1.0
 */
@EnableSwagger2Doc
@SpringBootApplication
public class MediaApplication {
    public static void main(String[] args) {
        SpringApplication.run(MediaApplication.class, args);
    }
}