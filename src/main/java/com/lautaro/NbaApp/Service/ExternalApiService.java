package com.lautaro.NbaApp.Service;

import com.lautaro.NbaApp.Controller.Dto.PlayerDto;
import com.lautaro.NbaApp.Controller.Dto.PlayerResponse;
import com.lautaro.NbaApp.Controller.Dto.TeamDto;
import com.lautaro.NbaApp.Controller.Dto.TeamResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ExternalApiService {
    private final WebClient webClient;

    @Autowired
    public ExternalApiService(WebClient webClient) {
        this.webClient = webClient;
    }

    //Método para obtener los equipos de la API
    public Mono<TeamDto[]> getTeams() {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/teams")
                        .build())
                .retrieve()
                .bodyToMono(TeamResponse.class) //Deserealiza a TeamResponse.
                .map(TeamResponse::getData)    //Extrae el arreglo de TeamDto.
                .doOnNext(teams -> {
                    for (TeamDto team : teams) {
                        System.out.println(team.toString());
                    }
                });
    }

    public Mono<PlayerDto[]> getAllPlayers() {
        return fetchPlayers(1, new ArrayList<>()); //Inicia la paginacion en 1 y una lista vacia.
    }

    //Método para obtener los jugadores de la API
    private Mono<PlayerDto[]> fetchPlayers(int page, List<PlayerDto> allPlayers) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/players")
                        .queryParam("page", page) //Agrega el parametro de la pagina
                        .queryParam("per_page", 100) // Establece el maximo de resultados por página.
                        .build())
                .retrieve()
                .bodyToMono(PlayerResponse.class)
                .flatMap(playerResponse -> {
                    List<PlayerDto> players = playerResponse.getData();
                    allPlayers.addAll(players); //Agrega los jugadores actuales a la lista de jugadores ya cargados.

                    //Verifica si hay mas jugadores que cargar.
                    if (playerResponse.getMeta().getNextCursor() != null) {
                        return fetchPlayers(playerResponse.getMeta().getNextCursor(), allPlayers);//Llama recursivamente para la siguiente pagina.
                    } else {
                        return Mono.just(allPlayers.toArray(new PlayerDto[0]));//Convierte la lista acumulada a un array y lo retorna.
                    }
                })
                .doOnNext(players -> {
                    for (PlayerDto playerDto : players) {
                        System.out.println(playerDto.toString()); //muestra cada jugador en la consola. Luego comentar!
                    }
                });
    }


}
