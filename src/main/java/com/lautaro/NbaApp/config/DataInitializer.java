package com.lautaro.NbaApp.config;

import com.lautaro.NbaApp.Repository.TeamRepository;
import com.lautaro.NbaApp.Service.ExternalApiService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    private final TeamRepository teamRepository;
    private final ExternalApiService externalApiService;

    public DataInitializer(TeamRepository teamRepository, ExternalApiService externalApiService) {
        this.teamRepository = teamRepository;
        this.externalApiService = externalApiService;
    }

    @Bean
    public ApplicationRunner initializeDatabase() {
        return args -> {
            //Verifica si la base de datos está vacía
            if (teamRepository.count() == 0) {
                //Llama al servicio para obtener y cargar los datos.
                externalApiService.fetchAndSaveDataFromApi();
            }
        };
    }
}
