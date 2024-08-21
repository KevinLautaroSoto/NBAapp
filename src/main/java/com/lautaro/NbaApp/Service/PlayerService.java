package com.lautaro.NbaApp.Service;

import com.lautaro.NbaApp.Controller.Dto.PlayerDto;
import com.lautaro.NbaApp.Models.Player;
import com.lautaro.NbaApp.Repository.PlayerRepository;
import com.lautaro.NbaApp.Utilities.PlayerMapper;
import com.lautaro.NbaApp.exceptions.CustomDatabaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {this.playerRepository = playerRepository;}

    //create player
    public ResponseEntity<String> createPlayer(PlayerDto playerDto) {
        try {
            Player newPlayer = new Player();
            PlayerMapper.mapToPlayer(newPlayer, playerDto);
            playerRepository.save(newPlayer);
            return ResponseEntity.ok("Player successfully created.");
        } catch (DataAccessException e) {
            return ResponseEntity.status(500).body("Error creating player: " + e.getMessage());
        }
    }

    //get all players
    public List<Player> getAllPlayers() {
        try {
            return playerRepository.findAll();
        } catch (DataAccessException e) {
            throw new CustomDatabaseException("Error retrieving player from the database. ", e);
        }
    }

    //get player by id
    public Optional<Player> getPlayerById (Long id) {
        try {
            return playerRepository.findById(id);
        } catch (DataAccessException e) {
            throw new CustomDatabaseException("Error retrieving player with ID: " + id + "form the database.", e);
        }
    }

    //update player
    public Player updatedPlayer(Long id, PlayerDto playerDto) {
        try {
            Player playerToUpdate = playerRepository.findById(id)
                    .orElseThrow(() -> new CustomDatabaseException("Team not found with ID: " + id));

            PlayerMapper.mapToPlayer(playerToUpdate, playerDto);

            return playerToUpdate;
        } catch (DataAccessException e) {
            throw new CustomDatabaseException("Error updating player with ID: " + id, e);
        }
    }

    //delete player
    public ResponseEntity<String> deleteTeam(Long id) {
        try {

        }
    }
}
