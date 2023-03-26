package com.goodstudy.system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Description: 跨域配置
 *
 * @Author: Jack
 * Date: 2023/03/26 16:42
 * Version: 1.0
 */
@Configuration
public class GlobalCorsConfig {

    /**
     * 跨域配置
     */
     @Bean
     public CorsFilter corsFilter() {
         CorsConfiguration config = new CorsConfiguration();
         //允许白名单域名进行跨域调用
         config.addAllowedOrigin("*");
         //允许跨域发送cookie
         config.setAllowCredentials(true);
         //放行全部原始头信息
         config.addAllowedHeader("*");
         //允许所有请求方法跨域调用
         config.addAllowedMethod("*");
         UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
         source.registerCorsConfiguration("/**", config);
         return new CorsFilter(source);
     }

}
