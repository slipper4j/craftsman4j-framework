package com.craftsman4j.framework.jackson.core.databind;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * LocalDateTime序列化规则
 * <p>
 * 会将LocalDateTime序列化为毫秒级时间戳
 */
public class LocalDateTime2DateSerializer extends JsonSerializer<LocalDateTime> {

    public static final LocalDateTime2DateSerializer INSTANCE = new LocalDateTime2DateSerializer();

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(FORMATTER.format(value));
    }
}
