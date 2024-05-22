package com.example.final_boss_spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

// Clase de configuración de la aplicación
@Configuration
public class ApplicationConfig {
    // Bean para realizar peticiones HTTP
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}