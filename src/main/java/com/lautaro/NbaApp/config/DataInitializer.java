package com.lautaro.NbaApp.config;

import com.lautaro.NbaApp.Repository.PlayerRepository;
import com.lautaro.NbaApp.Repository.TeamRepository;
import com.lautaro.NbaApp.Service.ExternalApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    private final ExternalApiService externalApiService;
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;

    @Autowired
    public DataInitializer(ExternalApiService externalApiService, PlayerRepository playerRepository, TeamRepository teamRepository) {
        this.externalApiService = externalApiService;
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
    }

    /**
     * Initializes the application by fetching and saving data from the external API if necessary.
     */
    public void initialize() {
        if (teamRepository.count() == 0) {
            externalApiService.getTeams().subscribe(response -> {
                System.out.println("Respuesta de los equipos: " + response);
            });
        } else {
            System.out.println("The database has already been loaded with the data of the teams.");
        }

        if (playerRepository.count() == 0) {
            // Obtener y mostrar los jugadores.
            externalApiService.getAllPlayers().subscribe(response -> {
                System.out.println("Respuesta de los jugadores: " + response);
            });
        } else {
            System.out.println("The databsae has already been loaded with the data of the players.");
        }
    }

    @Bean
    public ApplicationRunner applicationRunner() {
        return args -> {
            initialize(); //Ejecuta el método initialize al arrancar la aplicación.
        };
    }

}
