package com.ony.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.ony.serializer.CustomizeNullJsonSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author TonyLeung
 * @date 2022/4/2
 */
@Configuration
public class JacksonConfig {

    @Bean
    Jackson2ObjectMapperBuilderCustomizer customJackson() {
        return builder -> {
            // 本地化
            builder.locale(Locale.CHINA);
            // 时区
            builder.timeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));
            // 日期格式化
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            builder.dateFormat(simpleDateFormat);
            // 空值不返回
            // builder.serializationInclusion(JsonInclude.Include.NON_NULL);
            // 美化输出
            builder.featuresToEnable(SerializationFeature.INDENT_OUTPUT);
            // LONG -> String
            builder.serializerByType(Long.TYPE, ToStringSerializer.instance);
            builder.serializerByType(Long.class, ToStringSerializer.instance);

            // NULL值处理
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.getSerializerProvider()
                    .setNullValueSerializer(new CustomizeNullJsonSerializer.NullObjectJsonSerializer());

            builder.configure(objectMapper);
        };
    }

}
