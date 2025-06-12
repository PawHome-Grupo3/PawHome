package com.grupo3.pawHome.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://grupo03-desarrollo.serverjava.net/") // Adjust for your frontend URL
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE");
    }
}
