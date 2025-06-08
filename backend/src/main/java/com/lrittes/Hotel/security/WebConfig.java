// package com.lrittes.Hotel.security;

// import org.springframework.context.annotation.Configuration;
// import org.springframework.web.servlet.config.annotation.CorsRegistry;
// import org.springframework.web.servlet.config.annotation.EnableWebMvc;
// import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// @Configuration
// @EnableWebMvc
// public class WebConfig implements WebMvcConfigurer {
    
//     @Override
//     public void addCorsMappings(CorsRegistry registry) {
//         registry.addMapping("/**")
//                 .allowedOrigins("http://localhost:3000", "http://localhost:8080", "http://localhost:8082")
//                 .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//                 .allowedHeaders("*")
//                 .allowCredentials(true);
//     }
// }