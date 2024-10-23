package com.lautaro.NbaApp.Service;

import com.lautaro.NbaApp.Controller.Dto.PlayerDto;
import org.springframework.http.ResponseEntity;

public interface PlayerService {

    ResponseEntity<String> createPlayer(PlayerDto playerDto);

    ResponseEntity<?> getAllPlayers();

    ResponseEntity<?> getPlayerById(Long id);

    ResponseEntity<?> getPlayerByName(String name);

    ResponseEntity<?> updatePlayer(Long id, PlayerDto playerDto);

    ResponseEntity<?> patchPlayer(Long id, PlayerDto playerDto);

    ResponseEntity<String> deletePlayer(Long id);
}
