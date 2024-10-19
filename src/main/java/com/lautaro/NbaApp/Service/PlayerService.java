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
    private final TeamService teamService;

    @Autowired
    public PlayerService(PlayerRepository playerRepository, TeamService teamService) {
        this.playerRepository = playerRepository;
        this.teamService = teamService;
    }

    /**
     * Creates a new player based on the provided PlayerDto object.
     *
     * @param playerDto The PlayerDto object containing information about the player to be created.
     * @return A ResponseEntity object with appropriate HTTP status code and message.
     *         - On success: 201 Created with message "Player successfully created."
     *         - On failure: 500 Internal Server Error with detailed error message.
     */
    public ResponseEntity<String> createPlayer(PlayerDto playerDto) {
        try {
            Player newPlayer = new Player();
            PlayerMapper.mapToPlayer(newPlayer, playerDto, teamService);
            playerRepository.save(newPlayer);
            return ResponseEntity.status(HttpStatus.CREATED).body("Player successfully created.");
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error crating player: " + e.getMessage());
        }
    }

    /**
     * Retrieves all players stored in the database.
     *
     * @return A list containing all Player objects representing existing players.
     * @throws CustomDatabaseException Throws a custom exception if a data access error occurs.
     */
    public List<Player> getAllPlayers() {
        try {
            return playerRepository.findAll();
        } catch (DataAccessException e) {
            throw new CustomDatabaseException("Error retrieving player from the database. ", e);
        }
    }

    /**
     * Retrieves a specific player based on its unique identifier.
     *
     * @param id The unique identifier (Long) of the player to be retrieved.
     * @return An Optional object containing the requested Player object if found, or empty if not found.
     * @throws CustomDatabaseException Throws a custom exception if a data access error occurs.
     */
    public Optional<Player> getPlayerById (Long id) {
        try {
            return playerRepository.findById(id);
        } catch (DataAccessException e) {
            throw new CustomDatabaseException("Error retrieving player with ID: " + id + "form the database.", e);
        }
    }

    /**
     * Updates an existing player based on its identifier and provided PlayerDto object.
     *
     * @param id       The unique identifier (Long) of the player to be updated.
     * @param playerDto  The PlayerDto object containing updated information for the player.
     * @return The updated Player object after the update operation.
     * @throws CustomDatabaseException Throws a custom exception if a data access error occurs.
     */
    public Player updatePlayer(Long id, PlayerDto playerDto) {
        try {
            Player playerToUpdate = playerRepository.findById(id)
                    .orElseThrow(() -> new CustomDatabaseException("Player not found with ID: " + id));

            PlayerMapper.mapToPlayer(playerToUpdate, playerDto, teamService);
            return playerRepository.save(playerToUpdate);
        } catch (DataAccessException e) {
            throw new CustomDatabaseException("Error updating player with ID: " + id, e);
        }
    }

    /**
     * Deletes a player from the database based on its unique identifier.
     *
     * @param id The unique identifier (Long) of the player to be deleted.
     * @return A ResponseEntity object with appropriate HTTP status code and message.
     *         - On success: 200 OK with a success message indicating the deleted player's ID.
     *         - On failure: Appropriate error code (e.g., 404 Not Found) with an error message.
     */
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
