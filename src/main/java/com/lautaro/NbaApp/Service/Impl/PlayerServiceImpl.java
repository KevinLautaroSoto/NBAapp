package com.lautaro.NbaApp.Service.Impl;

import com.lautaro.NbaApp.Controller.Dto.PlayerDto;
import com.lautaro.NbaApp.Models.Player;
import com.lautaro.NbaApp.Repository.PlayerRepository;
import com.lautaro.NbaApp.Service.PlayerService;
import com.lautaro.NbaApp.Utilities.PlayerMapper;
import com.lautaro.NbaApp.exceptions.CustomDatabaseException;
import com.lautaro.NbaApp.exceptions.CustomNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final TeamServiceImpl teamService;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository, TeamServiceImpl teamServiceImpl) {
        this.playerRepository = playerRepository;
        this.teamService = teamServiceImpl;
    }

    /**
     * Creates a new player based on the provided PlayerDto object.
     *
     * @param playerDto The PlayerDto object containing information about the player to be created.
     * @return A ResponseEntity object with appropriate HTTP status code and message.
     *         - On success: 201 Created with message "Player successfully created."
     *         - On failure: 500 Internal Server Error with detailed error message.
     */
    @Override
    public ResponseEntity<String> createPlayer(PlayerDto playerDto) {
        try {
            Player newPlayer = new Player();
            PlayerMapper.mapToPlayer(newPlayer, playerDto, teamService);
            playerRepository.save(newPlayer);
            return ResponseEntity.status(HttpStatus.CREATED).body("Player successfully created.");
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating player: " + e.getMessage());
        }
    }

    /**
     * Retrieves all players stored in the database.
     *
     * @return A list containing all Player objects representing existing players.
     * @throws CustomDatabaseException Throws a custom exception if a data access error occurs.
     */
    @Override
    public ResponseEntity<?> getAllPlayers() {
        try {
            return ResponseEntity.ok(playerRepository.findAll());
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error getting player from the database.");
        }
    }

    /**
     * Retrieves a specific player based on its unique identifier.
     *
     * @param id The unique identifier (Long) of the player to be retrieved.
     * @return An Optional object containing the requested Player object if found, or empty if not found.
     * @throws CustomDatabaseException Throws a custom exception if a data access error occurs.
     */
    @Override
    public ResponseEntity<?> getPlayerById (Long id) {
        try {
            return ResponseEntity.ok(playerRepository.findById(id).orElseThrow(() -> new CustomNotFoundException("Player with that id couldn´t be found.")));
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error in getting a player with ID: " + id);
        }
    }

    /**
     * Retrieves plauers whose names contain the specified string, ignoring case.
     *
     * @param name The name (or part of it) to search for.
     * @return ResponseEntity containing the list of matching players or NOT_FOUND status.
     */
    @Override
    public ResponseEntity<?> getPlayerByName (String name) {
        try {
            List<Player> players = playerRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(name, name);

            if(!players.isEmpty()) {
                return ResponseEntity.ok(players);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No players found with name: " + name);
            }
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error accessing the database: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error ocurred: " + e.getMessage());
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
    @Override
    public ResponseEntity<?> updatePlayer(Long id, PlayerDto playerDto) {
        try {
            Player playerToUpdate = playerRepository.findById(id)
                    .orElseThrow(() -> new CustomDatabaseException("Player not found with ID: " + id));

            PlayerMapper.mapToPlayer(playerToUpdate, playerDto, teamService);

            playerRepository.save(playerToUpdate);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    //.path("") only use if the endopint require.
                    .buildAndExpand(id)
                    .toUri();

            return ResponseEntity.created(location).body(playerToUpdate);
        } catch (DataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Updates the fields of an existing player with the provided values from the PlayerDto.
     * Only non-null or non-zero values from the PlayerDto will be used to update the player.
     *
     * @param id        The ID of the player to be updated.
     * @param playerDto The data transfer object containing the new values for the player fields.
     * @return The updated Player entity after the patching process.
     * @throws CustomNotFoundException    If the player with the specified ID is not found.
     * @throws CustomDatabaseException    If there is a database error while updating the player.
     */
    @Override
    public ResponseEntity<?> patchPlayer(Long id, PlayerDto playerDto) {
        try {
            Player searchedPlayer = playerRepository.findById(id)
                    .orElseThrow(() -> new CustomNotFoundException("Player with ID " + id + " not found."));
            if (playerDto.getFirst_name() != null) {
                searchedPlayer.setFirstName(playerDto.getFirst_name());
            }
            if (playerDto.getLast_name() != null) {
                searchedPlayer.setLastName(playerDto.getLast_name());
            }

            if (playerDto.getJersey_number() != null) {
                searchedPlayer.setJersey_number(playerDto.getJersey_number());
            }
            if (playerDto.getPosition() != null) {
                searchedPlayer.setPosition(playerDto.getPosition());
            }
            if (playerDto.getHeight() != null) {
                searchedPlayer.setHeight(playerDto.getHeight());
            }
            if (playerDto.getWeight() != null) {
                searchedPlayer.setWeight(playerDto.getWeight());
            }
            if (playerDto.getCountry() != null) {
                searchedPlayer.setCountry(playerDto.getCountry());
            }
            if (playerDto.getCollege() != null) {
                searchedPlayer.setCollege(playerDto.getCollege());
            }
            if (playerDto.getDraft_year() != 0) {
                searchedPlayer.setDraft_year(playerDto.getDraft_year());
            }
            if (playerDto.getDraft_round() != 0) {
                searchedPlayer.setDraft_round(playerDto.getDraft_round());
            }
            if (playerDto.getDraft_number() != 0) {
                searchedPlayer.setDraft_number(playerDto.getDraft_number());
            }

            playerRepository.save(searchedPlayer);

            return ResponseEntity.noContent().build();
        } catch (DataAccessException e) {
            return ResponseEntity.notFound().build();
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
    @Override
    public ResponseEntity<String> deletePlayer(Long id) {
        try {
            Player playerToDelete = playerRepository.findById(id)
                    .orElseThrow(() -> new CustomDatabaseException("Player not found a player with ID: " + id));

            playerRepository.delete(playerToDelete);
            return ResponseEntity.noContent().build();
        } catch (DataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
