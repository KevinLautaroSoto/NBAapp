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

    @Autowired
    private TeamService(TeamRepository teamRepository) {this.teamRepository = teamRepository;}

    /**
     * Creates a new team adn persists it to the databsae.
     * @param teamDto The teamDto object containing information about the team to be created.
     * @return A ResponseEntity object with appropriate HTTP status code and message.
     *        - On success: 200 Ok with message "Team Successfully creates."
     *        - On failure: 500 Internal Server Error with detailed error message.
     */
    public ResponseEntity<String> createTeam(TeamDto teamDto) {
        try {
            Team newTeam = new Team();
            TeamMapper.mapToTeam(newTeam, teamDto);
            teamRepository.save(newTeam);
            return ResponseEntity.ok("Team successfully created.");
        } catch (DataAccessException e) {
            return ResponseEntity.status(500).body("Error creating team: " + e.getMessage());
        }
    }

    /**
     * Retrieves all teams stored in the database.
     *
     * @return A list containing all Team objects representing existing teams.
     * @throws CustomDatabaseException Throws a custom exception if a data access error occurs.
     */
    public List<Team> getAllTeam() {
        try {
            return teamRepository.findAll();
        } catch (DataAccessException e) {
            throw new CustomDatabaseException("Error retrieving teams from the database. ", e);
        }
    }

    /**
     * Retrieves a specific team based on its unique identifier.
     *
     * @param id The unique identifier (Long) of the team to be retrieved.
     * @return An Optional object containing the requested Team object if found, or empty if not found.
     * @throws CustomDatabaseException Throws a custom exception if a data access error occurs.
     */
    public Optional<Team> getTeamById(Long id) {
        try {
            return teamRepository.findById(id);
        } catch (DataAccessException e) {
            throw new CustomDatabaseException("Error retrieving team with ID " + id + "from the database.", e);
        }
    }

    /**
     * Updates an existing team based on its identifier and provided TeamDto object.
     *
     * @param id       The unique identifier (Long) of the team to be updated.
     * @param teamDto  The TeamDto object containing updated information for the team.
     * @return The updated Team object after the update operation.
     * @throws CustomDatabaseException Throws a custom exception if a data access error occurs.
     */
    public Team updateTeam (Long id, TeamDto teamDto) {
        try {
            Team teamUpdated = teamRepository.findById(id)
                    .orElseThrow(() -> new CustomDatabaseException("Team not found with ID: " + id));

            TeamMapper.mapToTeam(teamUpdated, teamDto);

            return teamRepository.save(teamUpdated);
        } catch (DataAccessException e) {
            throw new CustomDatabaseException("Error updating team with ID: " + id, e);
        }
    }

    /**
     * Deletes a team from the database based on its unique identifier.
     *
     * @param id The unique identifier (Long) of the team to be deleted.
     * @return A ResponseEntity object with appropriate HTTP status code and message.
     *         - On success: 200 OK with a success message indicating the deleted team's ID.
     *         - On failure: Appropriate error code (e.g., 404 Not Found) with an error message.
     */
    public ResponseEntity<String> deleteTeam(Long id) {
        try {
            Team teamToDelete = teamRepository.findById(id)
                    .orElseThrow(() -> new CustomDatabaseException("Team not found with ID: " + id));

            teamRepository.delete(teamToDelete);

            return ResponseEntity.ok("Team with ID: " + id +  " successfully deleted from the database.");
        } catch (DataAccessException e) {
            throw new CustomDatabaseException("Error deleting team from the database.");
        }
    }
}
