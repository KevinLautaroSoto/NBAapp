package com.lautaro.NbaApp.Service;

import com.lautaro.NbaApp.Controller.Dto.PlayerDto;
import com.lautaro.NbaApp.Models.Player;
import com.lautaro.NbaApp.Repository.PlayerRepository;
import com.lautaro.NbaApp.Utilities.PlayerMapper;
import com.lautaro.NbaApp.exceptions.CustomDatabaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
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
            return ResponseEntity.status(HttpStatus.CREATED).body("Player successfully created.");
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error crating player: " + e.getMessage());
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
    public Player updatePlayer(Long id, PlayerDto playerDto) {
        try {
            Player playerToUpdate = playerRepository.findById(id)
                    .orElseThrow(() -> new CustomDatabaseException("Player not found with ID: " + id));

            PlayerMapper.mapToPlayer(playerToUpdate, playerDto);
            return playerRepository.save(playerToUpdate);
        } catch (DataAccessException e) {
            throw new CustomDatabaseException("Error updating player with ID: " + id, e);
        }
    }

    //delete player
    public ResponseEntity<String> deletePlayer(Long id) {
        try {
            Player playerToDelete = playerRepository.findById(id)
                    .orElseThrow(() -> new CustomDatabaseException("Player not found a player with ID: " + id));

            playerRepository.delete(playerToDelete);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Player successfully deleted from the database.");
        } catch (DataAccessException e) {
            throw new CustomDatabaseException("Error deleting player from the database: ", e);
        }
    }
}
