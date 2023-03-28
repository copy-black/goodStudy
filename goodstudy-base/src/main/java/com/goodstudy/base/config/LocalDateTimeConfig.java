package com.goodstudy.base.config;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Description:
 *
 * @Author: Jack
 * Date: 2023/03/26 14:27
 * Version: 1.0
 */
@Configuration
public class LocalDateTimeConfig {

    /**
     * LocalDateTime序列化
     *  LocalDateTime -> String
     *  服务端返回数据给客户端时，需要将LocalDateTime转换为String
     * @return
     */
    @Bean
    public LocalDateTimeSerializer localDateTimeDeserializer() {
        return new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 反序列化
     * String -> LocalDateTime
     * 客户端传入服务端数据时，需要将String转换为LocalDateTime
     * @return
     */
    @Bean
    public LocalDateTimeDeserializer localDateTimeSerializer() {
        return new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 自定义Jackson序列化
     * @return
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return jacksonObjectMapperBuilder -> {
            jacksonObjectMapperBuilder.serializerByType(LocalDateTime.class, localDateTimeDeserializer());
            jacksonObjectMapperBuilder.deserializerByType(LocalDateTime.class, localDateTimeSerializer());
        };
    }
}
