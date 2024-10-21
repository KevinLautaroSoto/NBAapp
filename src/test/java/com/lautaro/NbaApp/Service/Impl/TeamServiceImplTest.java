package com.lautaro.NbaApp.Service.Impl;

import com.lautaro.NbaApp.Controller.Dto.TeamDto;
import com.lautaro.NbaApp.Models.Team;
import com.lautaro.NbaApp.Repository.TeamRepository;
import com.lautaro.NbaApp.exceptions.CustomDatabaseException;
import com.lautaro.NbaApp.exceptions.CustomNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TeamServiceImplTest {

    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private TeamServiceImpl teamService;

    @Test
    @DisplayName("createTeam - valid teamDto, team created successfully")
    void createTeam_withValidTeamDto_returnsCreatedStatus() {
        TeamDto teamDto = new TeamDto("Lakers", "Los Angeles", "West", "Pacific", "LAL", "Los Angeles Lakers");
        Team savedTeam = new Team(teamDto.getName(), teamDto.getCity(), teamDto.getAbbreviation(),
                teamDto.getDivision(), teamDto.getConference(), teamDto.getFull_name());

        ResponseEntity<String> response = teamService.createTeam(teamDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Team successfully created.", response.getBody());
    }

    @Test
    @DisplayName("createTeam - database exception occurs")
    void createTeam_whenDatabaseExceptionOccurs_returnsInternalServerError() {
        TeamDto teamDto = new TeamDto("Lakers", "Los Angeles", "West", "Pacific", "LAL", "Los Angeles Lakers");

        Mockito.doThrow(new DataAccessException("DB error") {}).when(teamRepository).save(Mockito.any(Team.class));

        ResponseEntity<String> response = teamService.createTeam(teamDto);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error creating team: DB error", response.getBody());
    }

    @Test
    @DisplayName("getAllTeam - successfully retrieves all teams")
    void getAllTeam_returnAllTeams() {
        List<Team> teams = List.of(new Team("Lakers", "Los Angeles", "West", "Pacific", "LAL", "Los Angeles Lakers"), new Team("Warriors", "San Francisco", "West", "Pacific", "GSW", "Golden State Warriors"));

        Mockito.when(teamRepository.findAll()).thenReturn(teams);

        ResponseEntity<?> response = teamService.getAllTeam();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(teams, response.getBody());
    }

    @Test
    @DisplayName("getAllTeam - database exception occurs")
    void getAllTeam_whenDatabaseExceptionOccurs_returnsNotFoundStatus() {
        Mockito.when(teamRepository.findAll()).thenThrow(new DataAccessException("DB error") {});

        ResponseEntity<?> response = teamService.getAllTeam();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Can´t retrieve the teams from the database. DB error", response.getBody());
    }

    @Test
    @DisplayName("getTeamById - team exists, returns team")
    void getTeamById_withValidId_returnsTeam() {
        Team team = new Team("Warriors", "San Francisco", "West", "Pacific", "GSW", "Golden State Warriors");
        Mockito.when(teamRepository.findById(1L)).thenReturn(Optional.of(team));

        ResponseEntity<?> response = teamService.getTeamById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(team, response.getBody());
    }

    @Test
    @DisplayName("getTeamById - team does not exist, throws exception")
    void getTeamById_withInvalidId_throwsNotFoundException() {
        Mockito.when(teamRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(CustomNotFoundException.class, () -> teamService.getTeamById(1L));
        assertEquals("Team with ID 1 not found.", exception.getMessage());
    }

    /**

    @Test
    void updateTeam_withValidIdAndTeamDto_returnsUpdatedTeam() {
        // Arrange
        Team team = new Team("Lakers", "Los Angeles", "West", "Pacific", "LAL", "Los Angeles Lakers");
        TeamDto teamDto = new TeamDto("Lakers", "Los Angeles", "West", "Pacific", "LAL", "Los Angeles Lakers");

        // Act
        ResponseEntity<?> response = teamService.updateTeam(1L, teamDto);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(teamDto, response.getBody());
    }
     */
    @Test
    void updateTeam_withInvalidId_throwsException() {

        Exception exception = assertThrows(CustomDatabaseException.class, () -> teamService.updateTeam(1L, new TeamDto()));
        assertEquals("Team not found with ID: 1", exception.getMessage());
    }


    @Test
    @DisplayName("patchTeam - valid ID, updates team partially")
    void patchTeam_withValidId_returnsNoContent() {
        Team team = new Team("Warriors", "San Francisco", "West", "Pacific", "GSW", "Golden State Warriors");
        TeamDto teamDto = new TeamDto();
        teamDto.setName("Warriors");

        Mockito.when(teamRepository.findById(1L)).thenReturn(Optional.of(team));

        ResponseEntity<?> response = teamService.patchTeam(1L, teamDto);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals("Warriors", team.getName());
    }

    @Test
    @DisplayName("patchTeam - team not found, throws exception")
    void patchTeam_withInvalidId_throwsException() {
        Mockito.when(teamRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(CustomNotFoundException.class, () -> teamService.patchTeam(1L, new TeamDto()));
        assertEquals("Team with ID 1 not found.", exception.getMessage());
    }


    @Test
    @DisplayName("deleteTeam - valid ID, deletes team successfully")
    void deleteTeam_withValidId_deletesTeam() {
        Team team = new Team("Lakers", "Los Angeles", "West", "Pacific", "LAL", "Los Angeles Lakers");
        Mockito.when(teamRepository.findById(1L)).thenReturn(Optional.of(team));

        ResponseEntity<String> response = teamService.deleteTeam(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(teamRepository, times(1)).delete(team);
    }

    @Test
    @DisplayName("deleteTeam - team not found, throws exception")
    void deleteTeam_withInvalidId_throwsException() {
        Mockito.when(teamRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(CustomDatabaseException.class, () -> teamService.deleteTeam(1L));
        assertEquals("Team not found with ID: 1", exception.getMessage());
    }

}