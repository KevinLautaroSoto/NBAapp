package com.lautaro.NbaApp.Service.Impl;

import com.lautaro.NbaApp.Controller.Dto.TeamDto;
import com.lautaro.NbaApp.Models.Team;
import com.lautaro.NbaApp.Repository.TeamRepository;
import com.lautaro.NbaApp.Service.TeamService;
import com.lautaro.NbaApp.Utilities.TeamMapper;
import com.lautaro.NbaApp.exceptions.CustomDatabaseException;
import com.lautaro.NbaApp.exceptions.CustomNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

    @Autowired
    private TeamServiceImpl(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    /**
     * Creates a new team adn persists it to the databsae.
     *
     * @param teamDto The teamDto object containing information about the team to be created.
     * @return A ResponseEntity object with appropriate HTTP status code and message.
     * - On success: 200 Ok with message "Team Successfully creates."
     * - On failure: 500 Internal Server Error with detailed error message.
     */
    @Override
    public ResponseEntity<String> createTeam(TeamDto teamDto) {
        try {
            Team newTeam = new Team();
            TeamMapper.mapToTeam(newTeam, teamDto);
            teamRepository.save(newTeam);
            return ResponseEntity.status(HttpStatus.CREATED).body("Team successfully created.");
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating team: " + e.getMessage());
        }
    }

    /**
     * Retrieves all teams stored in the database.
     *
     * @return A list containing all Team objects representing existing teams.
     * @throws CustomDatabaseException Throws a custom exception if a data access error occurs.
     */
    @Override
    public ResponseEntity<?> getAllTeam(Pageable pageable) {
        try {
            Page<Team> teamsPage = teamRepository.findAll(pageable);
            return ResponseEntity.ok(teamsPage);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Can´t retrieve the teams from the database. " + e.getMessage());
        }
    }

    /**
     * Retrieves a specific team based on its unique identifier.
     *
     * @param id The unique identifier (Long) of the team to be retrieved.
     * @return An Optional object containing the requested Team object if found, or empty if not found.
     * @throws CustomDatabaseException Throws a custom exception if a data access error occurs.
     */
    @Override
    public ResponseEntity<?> getTeamById(Long id) {
        try {
            return ResponseEntity.ok(teamRepository.findById(id)
                    .orElseThrow(() -> new CustomNotFoundException("Team with ID " + id + " not found."))
            );
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Retrieves teams whose names contain the specified string, ignoring case.
     *
     * @param name The name (or part of it) to search for.
     * @return ResponseEntity containing the list of matching teams or NOT_FOUND status.
     */
    @Override
    public ResponseEntity<?> getTeamByName(String name) {
        try {
            List<Team> teams = teamRepository.findByNameContainingIgnoreCase(name);

            if (!teams.isEmpty()) {
                return ResponseEntity.ok(teams);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No teams found containing name: " + name);
            }
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error accessing the database: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred: " + e.getMessage());
        }
    }

    /**
     * Updates an existing team based on its identifier and provided TeamDto object.
     *
     * @param id      The unique identifier (Long) of the team to be updated.
     * @param teamDto The TeamDto object containing updated information for the team.
     * @return The updated Team object after the update operation.
     * @throws CustomDatabaseException Throws a custom exception if a data access error occurs.
     */
    @Override
    public ResponseEntity<?> updateTeam(Long id, TeamDto teamDto) {
        try {
            Team teamUpdated = teamRepository.findById(id)
                    .orElseThrow(() -> new CustomDatabaseException("Team not found with ID: " + id));

            TeamMapper.mapToTeam(teamUpdated, teamDto);
            teamRepository.save(teamUpdated);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    //.path("") only use if the endopint require.
                    .buildAndExpand(id)
                    .toUri();

            //return ResponseEntity.created(location).build(); Use this if don´t want to show more information.

            return ResponseEntity.created(location).body(teamDto);
        } catch (DataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Partially updates an existing team entity based on its unique identifier.
     *
     * @param id      The unique identifier (Long) of the team to be updated.
     * @param teamDto The updated team data (TeamDto). Only fields with non-null values will be applied to the existing team.
     * @return The updated team entity (Team) on success, or throws an exception on failure.
     * @throws CustomNotFoundException if a team with the provided ID is not found.
     * @throws CustomDatabaseException if a database error occurs during the update operation.
     */
    @Override
    public ResponseEntity<?> patchTeam(Long id, TeamDto teamDto) {
        try {
            Team searchedTeam = teamRepository.findById(id)
                    .orElseThrow(() -> new CustomNotFoundException("Team with ID " + id + " not found."));
            if (teamDto.getName() != null) {
                searchedTeam.setName(teamDto.getName());
            }
            if (teamDto.getCity() != null) {
                searchedTeam.setCity(teamDto.getCity());
            }
            if (teamDto.getAbbreviation() != null) {
                searchedTeam.setAbbreviation(teamDto.getAbbreviation());
            }
            if (teamDto.getDivision() != null) {
                searchedTeam.setDivision(teamDto.getDivision());
            }
            if (teamDto.getConference() != null) {
                searchedTeam.setConference(teamDto.getConference());
            }
            if (teamDto.getFull_name() != null) {
                searchedTeam.setFull_name(teamDto.getFull_name());
            }

            teamRepository.save(searchedTeam);
            return ResponseEntity.noContent().build();
        } catch (DataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes a team from the database based on its unique identifier.
     *
     * @param id The unique identifier (Long) of the team to be deleted.
     * @return A ResponseEntity object with appropriate HTTP status code and message.
     * - On success: 200 OK with a success message indicating the deleted team's ID.
     * - On failure: Appropriate error code (e.g., 404 Not Found) with an error message.
     */
    @Override
    public ResponseEntity<String> deleteTeam(Long id) {
        try {
            Team teamToDelete = teamRepository.findById(id)
                    .orElseThrow(() -> new CustomDatabaseException("Team not found with ID: " + id));

            teamRepository.delete(teamToDelete);

            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
