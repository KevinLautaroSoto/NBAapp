package com.lautaro.NbaApp.Service;

import com.lautaro.NbaApp.Repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {this.playerRepository = playerRepository;}

    //create player
    public ResponseEntity<String> createPlayer() {

    }

    //get all players

    //get player by id

    //update player

    //delete player
}
