package com.craftsman4j.framework.jackson.core.databind;

import com.craftsman4j.framework.dict.core.util.DictFrameworkUtils;
import com.craftsman4j.framework.jackson.core.annotations.Dict;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.Objects;

public class DictSerializer extends StdSerializer<Object> implements ContextualSerializer {

    /**
     * 字典注解
     */
    private Dict dict;

    public DictSerializer() {
        super(Object.class);
    }

    public DictSerializer(Dict dict) {
        super(Object.class);
        this.dict = dict;
    }

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (Objects.isNull(value)) {
            gen.writeObject(null);
            return;
        }
        // 通过数据字典类型和value获取name
        String label = DictFrameworkUtils.getDictDataLabel(dict.type(), value.toString());
        gen.writeObject(value);
        gen.writeFieldName(dict.targetFiled());
        gen.writeObject(label);
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty beanProperty) throws JsonMappingException {
        if (Objects.isNull(beanProperty)) {
            return prov.findNullValueSerializer(null);
        }
        Dict dict = beanProperty.getAnnotation(Dict.class);
        if (Objects.nonNull(dict)) {
            return new DictSerializer(dict);
        }
        return prov.findValueSerializer(beanProperty.getType(), beanProperty);
    }
}
