package com.lautaro.NbaApp.Service;

import com.lautaro.NbaApp.Controller.Dto.PlayerDto;
import com.lautaro.NbaApp.Controller.Dto.PlayerResponse;
import com.lautaro.NbaApp.Controller.Dto.TeamDto;
import com.lautaro.NbaApp.Controller.Dto.TeamResponse;
import com.lautaro.NbaApp.Models.Player;
import com.lautaro.NbaApp.Models.Team;
import com.lautaro.NbaApp.Repository.PlayerRepository;
import com.lautaro.NbaApp.Repository.TeamRepository;
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
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;

    @Autowired
    public ExternalApiService(WebClient webClient, TeamRepository teamRepository, PlayerRepository playerRepository) {
        this.webClient = webClient;
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
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
                    //Save teams into the database.
                    Arrays.stream(teams)
                            .limit(30) //Limit 30 teams.
                            .forEach(teamDto -> {
                                Team team = new Team(); //Create a new entity to store the Team.
                                team.setName(teamDto.getName());
                                team.setCity(teamDto.getCity());
                                team.setConference(teamDto.getConference());
                                team.setDivision(teamDto.getDivision());
                                team.setAbbreviation(teamDto.getAbbreviation());
                                team.setFull_name(teamDto.getFull_name());
                                //Store the team into the database.
                                teamRepository.save(team);
                    });
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
                        .queryParam("page", page) //Add page param
                        .queryParam("per_page", 100) // set max results per page.
                        .build())
                .retrieve()
                .bodyToMono(PlayerResponse.class)
                .flatMap(playerResponse -> {
                    List<PlayerDto> players = playerResponse.getData();
                    allPlayers.addAll(players); //Agrega los jugadores actuales a la lista de jugadores ya cargados.

                    //Save players into the database.
                    players.forEach(playerDto -> {
                        Player player = new Player(); //Create player entity for storage.
                        player.setFirst_name(playerDto.getFirst_name());
                        player.setLast_name(playerDto.getLast_name());
                        player.setPosition(playerDto.getPosition());
                        player.setHeight(playerDto.getHeight());
                        player.setWeight(playerDto.getWeight());
                        player.setJersey_number(playerDto.getJersey_number());
                        player.setCountry((playerDto.getCountry()));
                        player.setCollege(playerDto.getCollege());
                        player.setDraft_year(playerDto.getDraft_year());
                        player.setDraft_round(playerDto.getDraft_round());
                        player.setDraft_number(playerDto.getDraft_number());
                        player.setTeam(playerDto.getTeam());
                        //save the player into the database.
                        playerRepository.save(player);
                    });

                    //Verifica si hay mas jugadores que cargar.
                    if (playerResponse.getMeta().getNextCursor() != null && playerResponse.getMeta().getNextCursor() > page) {
                        return fetchPlayers(page + 1, allPlayers);//Llama recursivamente para la siguiente pagina.
                    } else {
                        return Mono.just(allPlayers.toArray(new PlayerDto[0]));//Convierte la lista acumulada a un array y lo retorna.
                    }
                });
    }
}
