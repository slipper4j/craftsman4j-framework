package com.craftsman4j.framework.jackson.config;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import com.craftsman4j.framework.common.util.json.JsonUtils;
import com.craftsman4j.framework.jackson.core.databind.LocalDateTime2TimestampDeserializer;
import com.craftsman4j.framework.jackson.core.databind.LocalDateTime2TimestampSerializer;
import com.craftsman4j.framework.jackson.core.databind.NumberSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@AutoConfiguration
@Slf4j
public class JacksonAutoConfiguration {

    @Bean
    @SuppressWarnings("InstantiationOfUtilityClass")
    public JsonUtils jsonUtils(List<ObjectMapper> objectMappers) {
        // 1.1 创建 SimpleModule 对象
        SimpleModule simpleModule = new SimpleModule();
        simpleModule
                // 新增 Long 类型序列化规则，数值超过 2^53-1，在 JS 会出现精度丢失问题，因此 Long 自动序列化为字符串类型
                .addSerializer(Long.class, NumberSerializer.INSTANCE)
                .addSerializer(Long.TYPE, NumberSerializer.INSTANCE)
                // 新增 LocalDateTime 序列化、反序列化规则
                .addSerializer(LocalDateTime.class, LocalDateTime2TimestampSerializer.INSTANCE)
                .addDeserializer(LocalDateTime.class, LocalDateTime2TimestampDeserializer.INSTANCE)
                // 新增 LocalDate 序列化、反序列化规则
                .addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN)))
                .addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN)))
                // 新增 LocalTime 序列化、反序列化规则
                .addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(DatePattern.NORM_TIME_PATTERN)))
                .addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DatePattern.NORM_TIME_PATTERN)));
        // 1.2 注册到 objectMapper
        objectMappers.forEach(objectMapper -> objectMapper.registerModule(simpleModule));

        // 2. 设置 objectMapper 到 JsonUtils {
        JsonUtils.init(CollUtil.getFirst(objectMappers));
        return new JsonUtils();
    }

}
