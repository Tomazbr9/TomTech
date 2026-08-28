package com.tomazbr9.tomtech.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class CorsConfig {

    private final CorsProperties corsProperties;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Origens permitidas
        configuration.setAllowedOrigins(corsProperties.getAllowedOrigins());

        // Métodos HTTP que o front pode disparar
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));

        // Cabeçalhos permitidos (Crucial para o Authorization com o Token JWT)
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "X-Requested-With"));

        // Permite o envio de cookies ou credenciais de autenticação se necessário
        configuration.setAllowCredentials(true);

        // Cache da pré-requisição (OPTIONS) por 1 hora para economizar tráfego de rede
        configuration.setMaxAge(3600L);

        // Aplica essa regra para absolutamente todas as rotas de todos os módulos
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}