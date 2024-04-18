package com.tn.esprit.kanbanboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class KanbanBoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(KanbanBoardApplication.class, args);
	}
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("http://localhost:4200") // Replace with your Angular app URL
						.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allowed HTTP methods
						.allowedHeaders("*") // Allowed request headers (you can customize this based on your requirements)
						.allowCredentials(true);
			}
		};
	}
}
