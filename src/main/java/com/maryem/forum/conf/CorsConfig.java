package com.maryem.forum.conf;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200") // Ajoutez les origines autorisées
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Ajoutez les méthodes HTTP autorisées
                .allowedHeaders("*"); // Ajoutez les en-têtes autorisés
        // Allow WebSocket handshake requests
        registry.addMapping("/user-websocket/**")
                .allowedOrigins("http://localhost:4200") // Add allowed origins for WebSocket
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Add allowed methods for WebSocket
                .allowedHeaders("*") // Add allowed headers for WebSocket
                .allowCredentials(true); // Allow credentials for WebSocket
    }
}