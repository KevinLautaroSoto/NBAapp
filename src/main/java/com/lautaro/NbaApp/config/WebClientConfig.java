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
                .defaultHeader("x-rapidapi-key", "238c8f4ec8mshfd0c9d4a35b3bbfp1527bcjsn9736c4541a87") // Llave API
                .defaultHeader("x-rapidapi-host", "nba-stats4.p.rapidapi.com") // Host de la API
                .build();
    }
}
