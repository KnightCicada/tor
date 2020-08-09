package com.tor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.servlet.MultipartConfigElement;

@EnableScheduling
@SpringBootApplication
@Configuration
public class TorApplication {

    public static void main(String[] args) {
        SpringApplication.run(TorApplication.class, args);
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //允许上传的文件最大值
        factory.setMaxFileSize("1000MB"); //KB,MB
        /// 设置总上传数据总大小
        factory.setMaxRequestSize("100000MB");
        ;
        return factory.createMultipartConfig();
    }
}
