package com.lautaro.NbaApp.Controller;

import com.lautaro.NbaApp.Controller.Dto.PlayerDto;
import com.lautaro.NbaApp.Models.Player;
import com.lautaro.NbaApp.Service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/player")
public class PlayerController {
    @Autowired
    private PlayerService playerService;

    @PostMapping
    public void createPlayer(@RequestBody PlayerDto playerDto) {
        playerService.createPlayer(playerDto);
    }

    @GetMapping
    public List<Player> getAllPlayers() {
       return playerService.getAllPlayers();
    }

    @GetMapping("/{id}")
    public Optional<Player> getPlayersById(@PathVariable Long id) {
        return playerService.getPlayerById(id);
    }

    @PostMapping("/{id}")
    public Player updatePlayer(@PathVariable Long id, @RequestBody PlayerDto playerDto) {
        return playerService.updatePlayer(id, playerDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePlayer (@PathVariable Long id) {
        return playerService.deletePlayer(id);
    }
}
