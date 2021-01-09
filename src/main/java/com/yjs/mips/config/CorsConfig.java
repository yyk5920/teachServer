package com.yjs.mips.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")//url部分
                .allowedHeaders("*")
                .allowedMethods("*")
                .maxAge(1800)
                .allowCredentials(true)
                .allowedMethods("GET","POST", "PUT", "DELETE")
        //是否发送Cookie信息
//        config.setAllowCredentials(true);
//        //放行哪些原始域(请求方式)
//        config.addAllowedMethod("*");
//        //放行哪些原始域(头部信息)
//        config.addAllowedHeader("*");
                .allowedOrigins("*");
    }
    @Value("${web.windows-file-path}")
    private String windowsRootPath;

    @Value("${web.linux-file-path}")
    private String linuxRootPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")) {  //如果是Windows系统
            registry.addResourceHandler("/api/img/**")
                    .addResourceLocations("file:"+windowsRootPath); //媒体资源绝对路径
        } else {  //linux 和mac
            registry.addResourceHandler("/api/img/**")
                    .addResourceLocations("file:"+linuxRootPath);   //媒体资源绝对路径
        }
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }



}
