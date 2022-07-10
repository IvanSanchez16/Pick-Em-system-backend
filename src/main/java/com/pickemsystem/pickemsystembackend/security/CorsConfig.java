package com.pickemsystem.pickemsystembackend.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**")
                .allowedOriginPatterns("http://localhost")
                .allowedMethods("GET", "PUT", "POST", "DELETE", "OPTIONS")
                .allowCredentials(true);
    }
}
