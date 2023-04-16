package com.goodstudy.content;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Description: 内容服务启动类
 *
 * @Author: Jack
 * Date: 2023/03/25 18:19
 * Version: 1.0
 */
@EnableFeignClients(basePackages={"com.goodstudy.content.feignclient"})
@EnableSwagger2Doc
@SpringBootApplication
public class ContentApplication {
    public static void main(String[] args) {
        SpringApplication.run(ContentApplication.class, args);
    }
}
