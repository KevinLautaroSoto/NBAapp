package com.lautaro.NbaApp.Service;

import com.lautaro.NbaApp.Controller.Dto.TeamDto;
import com.lautaro.NbaApp.Models.Team;
import com.lautaro.NbaApp.Repository.TeamRepository;
import com.lautaro.NbaApp.Utilities.TeamMapper;
import com.lautaro.NbaApp.exceptions.CustomDatabaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private TeamMapper teamMapper;

    @Autowired
    private TeamService(TeamRepository teamRepository) {this.teamRepository = teamRepository;}

    //create team
    public ResponseEntity<String> createProduct(TeamDto teamDto) {
        try {
            Team newTeam = new Team();
            TeamMapper.mapToTeam(newTeam, teamDto);
            teamRepository.save(newTeam);
            return ResponseEntity.ok("Team successfully created.");
        } catch (DataAccessException e) {
            return ResponseEntity.status(500).body("Error creating team: " + e.getMessage());
        }
    }

    //get all teams
    public List<Team> getAllTeam() {
        try {
            return teamRepository.findAll();
        } catch (DataAccessException e) {
            throw new CustomDatabaseException("Error retrieving teams from the database. ", e);
        }
    }

    //get team by id
    public Optional<Team> getTeamById(Long id) {
        try {
            return teamRepository.findById(id);
        } catch (DataAccessException e) {
            throw new CustomDatabaseException("Error retrieving team with ID " + id + "from the database.", e);
        }
    }

    //update team
    public Team updateTeam (Long id, TeamDto teamDto) {
        try {
            Team teamUpdated = teamRepository.findById(id)
                    .orElseThrow(() -> new CustomDatabaseException("Team not found with ID: " + id));

            teamUpdated.setName(teamDto.getName());
            teamUpdated.setCity(teamDto.getCity());
            teamUpdated.setConference(teamDto.getConference());
            teamUpdated.setPrimaryColor(teamDto.getPrimaryColor());
            teamUpdated.setSecondaryColor(teamDto.getSecondaryColor());
            teamUpdated.setTerciaryColor(teamDto.getTerciaryColor());
            teamUpdated.setLogoUrl(teamDto.getLogoUrl());
            teamUpdated.setStadiumName(teamDto.getStadiumName());
            teamUpdated.setHeadcoach(teamDto.getHeadcoach());

            return teamRepository.save(teamUpdated);
        } catch (DataAccessException e) {
            throw new CustomDatabaseException("Error updating team with ID: " + id, e);
        }
    }

    //delete team
    public ResponseEntity<String> deleteTeam(Long id) {
        try {
            Team teamToDelete = teamRepository.findById(id)
                    .orElseThrow(() -> new CustomDatabaseException("Team not found with ID: " + id));

            teamRepository.delete(teamToDelete);

            return ResponseEntity.ok("Team successfully deleted from the database.");
        } catch (DataAccessException e) {
            throw new CustomDatabaseException("Error deleting team from the database.");
        }
    }
}
