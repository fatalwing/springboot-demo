package com.townmc.boot.configuration;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;

import javax.servlet.MultipartConfigElement;

@Configuration
public class MultipartConfig {
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //  单个数据大小
        factory.setMaxFileSize(DataSize.of(200, DataUnit.MEGABYTES));
        /// 总上传数据大小
        factory.setMaxRequestSize(DataSize.of(200, DataUnit.MEGABYTES));
        return factory.createMultipartConfig();
    }
}
