package com.lautaro.NbaApp.Service;

import com.lautaro.NbaApp.Models.Team;
import com.lautaro.NbaApp.Repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ExternalApiService {
    private final WebClient webClient;

    @Autowired
    public ExternalApiService(WebClient webClient) {
        this.webClient = webClient;
    }

    //Método para obtener los equipos de la API
    public Mono<String> getTeams() {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/teams")
                        .queryParam("per_page", 50)
                        .queryParam("page", 1)
                        .build())
                .retrieve()
                .bodyToMono(String.class);
    }

    //Método para obtener los jugadores de la API
    public Mono<String> getPlayers() {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/players")
                        .queryParam("page", 1)
                        .queryParam("per_page", 50)
                        .build())
                .retrieve()
                .bodyToMono(String.class);
    }
}
