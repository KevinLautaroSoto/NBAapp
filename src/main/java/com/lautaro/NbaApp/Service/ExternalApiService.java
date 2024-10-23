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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ExternalApiService {
    private final WebClient webClient;
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;

    private int cursor = 0;

    @Autowired
    public ExternalApiService(WebClient webClient, TeamRepository teamRepository, PlayerRepository playerRepository) {
        this.webClient = webClient;
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
    }

    /**
     * Fetches teams from the external API and saves them to the database.
     *
     * @return A Mono containing an array of TeamDto objects representing retrieved teams.
     */
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

    /**
     * Initiates the process of fetching all players in a paginated manner
     * from the external API and saves them to the database.
     *
     * @return A Mono containing an array of PlayerDto objects representing all players.
     */
    public Mono<PlayerDto[]> getAllPlayers() {
        return fetchPlayers(cursor, new ArrayList<>()); //Inicia la paginacion en 1 y una lista vacia.
    }

    /**
     * Fetches a page of players from the external API, saves them to the database,
     * and recursively calls itself if there are more pages to retrieve.
     *
     * @param cursor   The current page cursor for pagination.
     * @param allPlayers A list to accumulate all retrieved players.
     * @return A Mono containing an array of PlayerDto objects representing a page of players.
     */
    private Mono<PlayerDto[]> fetchPlayers(int cursor, List<PlayerDto> allPlayers) {
        return Flux.interval(Duration.ofSeconds(2))
                .take(1) //take one event to make the request.
                .flatMap(ignored -> webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/players")
                        .queryParam("cursor", cursor) //Add page param
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
                        player.setFirstName(playerDto.getFirst_name());
                        player.setLastName(playerDto.getLast_name());
                        player.setPosition(playerDto.getPosition());
                        player.setHeight(playerDto.getHeight());
                        player.setWeight(playerDto.getWeight());

                        //player.setJersey_number(playerDto.getJersey_number());
                        try {
                            String jerseyNumber = playerDto.getJersey_number();
                            player.setJersey_number(jerseyNumber);
                        } catch (Exception e) {
                            player.setJersey_number(null);
                        }

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
                    if (players.size() == 100) {
                        return fetchPlayers(cursor + 100, allPlayers);//Llama recursivamente para la siguiente pagina.
                    } else {
                        return Mono.just(allPlayers.toArray(new PlayerDto[0]));//Convierte la lista acumulada a un array y lo retorna.
                    }
                }))
                .next()
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(5)));
    }
}
