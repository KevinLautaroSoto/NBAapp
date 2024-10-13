package com.lautaro.NbaApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient (WebClient.Builder builder) {
        return builder
                .baseUrl("https://nba-stats4.p.rapidapi.com") // URL base para la API
                .defaultHeader("Authorization", "13b0929f-bc00-42f8-ad34-946ebbcd38cb") // Llave API
                .build();
    }
}
