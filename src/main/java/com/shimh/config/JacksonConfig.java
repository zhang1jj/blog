package com.shimh.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class JacksonConfig implements WebMvcConfigurer {

    // 注册 Hibernate 模块
    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        // 关键：注册 Hibernate 模块，并启用对延迟加载对象的处理
        Hibernate5Module hibernateModule = new Hibernate5Module();
        // 可选：如果需要序列化未加载的延迟属性（默认会忽略，可能导致数据不全）
        // hibernateModule.enable(Hibernate5Module.Feature.FORCE_LAZY_LOADING);
        objectMapper.registerModule(hibernateModule);

        return new MappingJackson2HttpMessageConverter(objectMapper);
    }

    // 强制使用自定义的消息转换器
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.clear(); // 清除默认转换器（谨慎使用，确保无其他影响）
        converters.add(mappingJackson2HttpMessageConverter());
    }
}
