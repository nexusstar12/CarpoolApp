package com.fantasticfour.poolapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Value("${frontend.url}")
    private String frontEndUrl;
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // All endpoints in the API
                .allowedOriginPatterns("*") // Your frontend app origin
                .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS") // Allowed request methods
                .allowCredentials(true) // Credentials, cookies, HTTP authentication
                .maxAge(3600) // Pre-flight request cache duration
                .allowedHeaders("*"); // Allowed headers
    }
}
