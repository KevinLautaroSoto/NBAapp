package com.lautaro.NbaApp.config;

import com.lautaro.NbaApp.Repository.TeamRepository;
import com.lautaro.NbaApp.Service.ExternalApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    private final ExternalApiService externalApiService;

    @Autowired
    public DataInitializer(ExternalApiService externalApiService) {
        this.externalApiService = externalApiService;
    }

    public void initialize() {
        // Obtener y mostrar los equipos.
        externalApiService.getTeams().subscribe(response -> {
            System.out.println("Respuesta de los equipos: " + response);
        });

        // Obtener y mostrar los jugadores.
        externalApiService.getAllPlayers().subscribe(response -> {
            System.out.println("Respuesta de los jugadores: " + response);
        });
    }

    @Bean
    public ApplicationRunner applicationRunner() {
        return args -> {
            initialize(); //Ejecuta el método initialize al arrancar la aplicación.
        };
    }
}
