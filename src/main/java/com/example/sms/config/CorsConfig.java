package com.example.sms.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// Configuration class to handle CORS (Cross-Origin Resource Sharing)
// This allows frontend running on different port to make requests to backend
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    // Override this method to configure CORS settings
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        
        // Allow all endpoints ("/api/**") to receive requests from any origin
        registry.addMapping("/api/**")
                // Allow requests from any origin (http://localhost:3000, http://localhost:63342, etc.)
                .allowedOrigins("*")
                // Allow these HTTP methods: GET, POST, PUT, DELETE, OPTIONS
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                // Allow any headers in the request
                .allowedHeaders("*");
    }
}
