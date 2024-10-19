package com.lautaro.NbaApp.Controller;

import com.lautaro.NbaApp.Controller.Dto.TeamDto;
import com.lautaro.NbaApp.Models.Team;
import com.lautaro.NbaApp.Service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/team")
public class TeamController {
    @Autowired
    private TeamService teamService;

    /**
     *Create a new team based on the provided TeamDto pbject.
     * @param teamDto the TeamDto object ocntaining information about the team to be created.
     *                teamDto includes fielsd like name, description, etc.
     * @throws Exception throws an exception if an error occurs during team creation.
     */
    @PostMapping
    public void createTeam (@RequestBody TeamDto teamDto) {
        teamService.createTeam(teamDto);
    }

    /**
     * Retrieves all teams stored in the database.
     *
     * @return A list containing all Team objects representing existing teams.
     */
    @GetMapping
    public List<Team> getAllTeam () {
        return teamService.getAllTeam();
    }

    /**
     * Retrieves a specific team based on its unique identifier.
     *
     * @param id The unique identifier (Long) of the team to be retrieved.
     * @return An Optional object containing the requested Team object if found, or empty if not found.
     */
    @GetMapping("/{id}")
    public Optional<Team> getTeamById (@PathVariable Long id) {
        return teamService.getTeamById(id);
    }

    /**
     * Updates an existing team based on its identifier and provided TeamDto object.
     *
     * @param id       The unique identifier (Long) of the team to be updated.
     * @param teamDto  The TeamDto object containing updated information for the team.
     * @return The updated Team object after the update operation.
     * @throws Exception Throws an exception if an error occurs during team update.
     */
    @PutMapping("/{id}")
    public Team updateTeam (@PathVariable Long id, @RequestBody TeamDto teamDto) {
        return teamService.updateTeam(id, teamDto);
    }

    /**
     * Deletes a team from the database based on its unique identifier.
     *
     * @param id The unique identifier (Long) of the team to be deleted.
     * @return A ResponseEntity object with appropriate HTTP status code and message.
     *         - On success: 200 OK with a success message.
     *         - On failure: Appropriate error code (e.g., 404 Not Found) with an error message.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTeam (@PathVariable Long id) {
        return teamService.deleteTeam(id);
    }
}
