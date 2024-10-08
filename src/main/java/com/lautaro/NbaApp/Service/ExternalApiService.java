package com.lautaro.NbaApp.Service;

import com.lautaro.NbaApp.Models.Team;
import com.lautaro.NbaApp.Repository.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class ExternalApiService {
    private final WebClient webClient;
    private final TeamRepository teamRepository;


    public ExternalApiService(WebClient webClient, TeamRepository teamRepository) {
        this.webClient = webClient;
        this.teamRepository = teamRepository;
    }

    //Método que hace la llamada a la API externa y guarda los dato sne la base de datos.
    public void fetchAndSaveDataFromApi() {
        //hacer la llamada get a la API externa
        List<Team> dataFromApi = webClient.get()
                .uri("/endpoint") //Define el endpoint de la API externa.
                .retrieve() //Realiza la llamada
                .bodyToMono(new ParametrizedTypeReference<List<Team>>() {}) // Convierte la respueste en List<Team>
                .block(); //Bloquea la ejecución hasta recibir la respuesta.

        // Guardar los datos en la base de datos
        if (dataFromApi != null && !dataFromApi.isEmpty()) {
            teamRepository.saveAll(dataFromApi);
        }
    }
}
