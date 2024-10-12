package com.lautaro.NbaApp.Service;

import com.lautaro.NbaApp.Models.Team;
import com.lautaro.NbaApp.Repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
                .uri("/teams/?per_page=50&page=1") // URI específica para los equipos
                .retrieve()
                .bodyToMono(String.class); // Obtén el cuerpo de la respuesta como un String
    }

    //Método para obtener los jugadores de la API
    public Mono<String> getPlayers() {
        return webClient.get()
                .uri("/players/?page=1&per_page=50") // URI específica para los jugadores
                .retrieve()
                .bodyToMono(String.class); // Obtén el cuerpo de la respuesta como un String
    }
}
