package com.lautaro.NbaApp.Service.Impl;

import com.lautaro.NbaApp.Controller.Dto.PlayerDto;
import com.lautaro.NbaApp.Models.Player;
import com.lautaro.NbaApp.Repository.PlayerRepository;
import com.lautaro.NbaApp.exceptions.CustomDatabaseException;
import com.lautaro.NbaApp.exceptions.CustomNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlayerServiceImplTest {

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private TeamServiceImpl teamService;

    @InjectMocks
    private PlayerServiceImpl playerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }


    @Test
    @DisplayName("createPlayer - Successfully creates a player")
    void createPlayer_Success() {
        PlayerDto playerDto = new PlayerDto();
        Player player = new Player();

        when(playerRepository.save(any(Player.class))).thenReturn(player);

        ResponseEntity<String> response = playerService.createPlayer(playerDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Player successfully created.", response.getBody());
    }

    @Test
    @DisplayName("createPlayer - Handles database exceptions")
    void createPlayer_DatabaseError() {
        PlayerDto playerDto = new PlayerDto();

        when(playerRepository.save(any(Player.class))).thenThrow(new DataAccessException("Database Error") {
        });

        ResponseEntity<String> response = playerService.createPlayer(playerDto);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error creating player: Database Error", response.getBody());
    }

    @Test
    @DisplayName("getPlayerById - Successfully retrieves player by ID")
    void getPlayerById_Success() {
        Player player = new Player();
        when(playerRepository.findById(1L)).thenReturn(Optional.of(player));

        ResponseEntity<?> response = playerService.getPlayerById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(player, response.getBody());
    }

    @Test
    @DisplayName("getPlayerById - Throws CustomNotFoundException when player not found")
    void getPlayerById_PlayerNotFound() {
        when(playerRepository.findById(1L)).thenReturn(Optional.empty());

        CustomNotFoundException exception = assertThrows(CustomNotFoundException.class, () -> {
            playerService.getPlayerById(1L);
        });

        assertEquals("Player with that id couldn´t be found.", exception.getMessage());
    }

    @Test
    @DisplayName("getPlayerByName - ")
    void getPlayerByName_Success() {
        List<Player> players = List.of(new Player(
                "LeBron",
                "James",
                "23",
                "Forward",
                "6-9",
                "250 lbs",
                "USA",
                "None",
                2003,
                1,
                1,
                null
        ), new Player(
                "Stephen",
                "Curry",
                "30",
                "Guard",
                "6'2\"",
                "185 lbs",
                "USA",
                "Davidson",
                2009,
                1,
                7,
                null
        ));

        when(playerRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase("lebron", "lebron"))
                .thenReturn(players);

        ResponseEntity<?> response = playerService.getPlayerByName("lebron");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(players, response.getBody());
    }

    @Test
    @DisplayName("getPlayerByName - ")
    void getPlayersByName_NotFound() {
        when(playerRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase("unknown", "unknown"))
                .thenReturn(List.of());

        ResponseEntity<?> response = playerService.getPlayerByName("unknown");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("No players found with name: unknown", response.getBody());
    }

    @Test
    @DisplayName("getPlayerByName - ")
    void getPlayerByName_DataAccessException() {
        when(playerRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase("lebron", "lebron"))
                .thenThrow(new DataAccessException("Database access error") {
                });

        ResponseEntity<?> response = playerService.getPlayerByName("lebron");

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error accessing the database: Database access error", response.getBody());
    }

    @Test
    @DisplayName("updatePlayer - Successfully updates a player")
    void updatePlayer_Success() {
        Player player = new Player();
        PlayerDto playerDto = new PlayerDto();

        when(playerRepository.findById(1L)).thenReturn(Optional.of(player));
        when(playerRepository.save(any(Player.class))).thenReturn(player);

        ResponseEntity<?> response = playerService.updatePlayer(1L, playerDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(player, response.getBody());
    }

    @Test
    @DisplayName("updatePlayer - Throws exception when player not found")
    void updatePlayer_PlayerNotFound() {
        PlayerDto playerDto = new PlayerDto();

        when(playerRepository.findById(1L)).thenReturn(Optional.empty());

        CustomDatabaseException exception = assertThrows(CustomDatabaseException.class, () -> {
            playerService.updatePlayer(1L, playerDto);
        });

        assertEquals("Player not found with ID: 1", exception.getMessage());
    }

    @Test
    @DisplayName("patchPlayer - successfully updates player with non-null values in PlayerDto")
    void patchPlayer_withValidIdAndNonNullPlayerDto_updatesPlayer() {
        Long playerId = 1L;
        Player existingPlayer = new Player();
        existingPlayer.setId(playerId);
        existingPlayer.setFirstName("Michael");
        existingPlayer.setLastName("Jordan");

        PlayerDto playerDto = new PlayerDto();
        playerDto.setFirst_name("Kobe");
        playerDto.setHeight("198");
        when(playerRepository.findById(playerId)).thenReturn(Optional.of(existingPlayer));

        when(playerRepository.save(any(Player.class))).thenReturn(existingPlayer);

        ResponseEntity<?> response = playerService.patchPlayer(playerId, playerDto);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals("Kobe", existingPlayer.getFirstName());  // El nombre debe haber cambiado
        assertEquals("198", existingPlayer.getHeight());         // La altura debe haber cambiado

        verify(playerRepository).save(existingPlayer);
    }

    @Test
    @DisplayName("patchPlayer - throws CustomNotFoundException when player ID does not exist")
    void patchPlayer_withInvalidId_throwsCustomNotFoundException() {
        Long invalidPlayerId = 999L;
        PlayerDto playerDto = new PlayerDto();
        playerDto.setFirst_name("Kobe");

        when(playerRepository.findById(invalidPlayerId)).thenReturn(Optional.empty());

        CustomNotFoundException exception = assertThrows(CustomNotFoundException.class, () -> {
            playerService.patchPlayer(invalidPlayerId, playerDto);
        });

        assertEquals("Player with ID 999 not found.", exception.getMessage());

        verify(playerRepository, never()).save(any(Player.class));
    }

    @Test
    @DisplayName("patchPlayer - returns 404 when DataAccessException occurs")
    void patchPlayer_whenDataAccessExceptionThrown_returns404() {
        Long playerId = 1L;
        Player existingPlayer = new Player();
        existingPlayer.setId(playerId);
        existingPlayer.setFirstName("Michael");

        PlayerDto playerDto = new PlayerDto();
        playerDto.setFirst_name("Kobe");

        when(playerRepository.findById(playerId)).thenReturn(Optional.of(existingPlayer));

        when(playerRepository.save(any(Player.class))).thenThrow(new DataAccessException("Error accessing database") {
        });

        ResponseEntity<?> response = playerService.patchPlayer(playerId, playerDto);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(playerRepository).save(existingPlayer);
    }

    @Test
    @DisplayName("deletePlayer - Successfully deletes a player")
    void deletePlayer_Success() {
        Player player = new Player();

        when(playerRepository.findById(1L)).thenReturn(Optional.of(player));
        doNothing().when(playerRepository).delete(player);

        ResponseEntity<String> response = playerService.deletePlayer(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @DisplayName("deletePlayer - Throws exception when player not found")
    void deletePlayer_PlayerNotFound() {
        when(playerRepository.findById(1L)).thenReturn(Optional.empty());

        CustomDatabaseException exception = assertThrows(CustomDatabaseException.class, () -> {
            playerService.deletePlayer(1L);
        });

        assertEquals("Player not found a player with ID: 1", exception.getMessage());
    }
}