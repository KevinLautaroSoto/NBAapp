package com.lautaro.NbaApp.Controller;

import com.lautaro.NbaApp.Controller.Dto.TeamDto;
import com.lautaro.NbaApp.Service.Impl.TeamServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/team")
public class TeamController {
    @Autowired
    private TeamServiceImpl teamServiceImpl;

    /**
     *Create a new team based on the provided TeamDto pbject.
     * @param teamDto the TeamDto object ocntaining information about the team to be created.
     *                teamDto includes fielsd like name, description, etc.
     * @throws Exception throws an exception if an error occurs during team creation.
     */
    @PostMapping
    public ResponseEntity<String> createTeam (@RequestBody TeamDto teamDto) {
        return teamServiceImpl.createTeam(teamDto);
    }

    /**
     * Retrieves all teams stored in the database.
     *
     * @return A list containing all Team objects representing existing teams.
     */
    @GetMapping
    public ResponseEntity<?> getAllTeam () {
        return teamServiceImpl.getAllTeam();
    }

    /**
     * Retrieves a specific team based on its unique identifier.
     *
     * @param id The unique identifier (Long) of the team to be retrieved.
     * @return An Optional object containing the requested Team object if found, or empty if not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getTeamById (@PathVariable Long id) {
        return teamServiceImpl.getTeamById(id);
    }

    /**
     * Updates an existing team based on its identifier and provided TeamDto object.
     *
     * @param id       The unique identifier (Long) of the team to be updated.
     * @param teamDto  The TeamDto object containing updated information for the team.
     * @return The updated Team object after the update operation.
     * @throws Exception Throws an exception if an error occurs during team update.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateTeam (@PathVariable Long id, @RequestBody TeamDto teamDto) {
        return teamServiceImpl.updateTeam(id, teamDto);
    }

    /**
     * Partially  
     updates an existing team based on its unique identifier.
     *
     * @param id       The unique identifier (Long) of the team to be updated.
     * @param teamDto The updated team data (TeamDto). Only the fields that need to be modified should be included.
     * @return A ResponseEntity object with the updated team (Team) or an appropriate error response.
     *         - On success: 200 OK with the updated team.
     *         - On failure (e.g., team not found, validation errors): Appropriate error code (e.g., 404 Not Found, 400 Bad Request) with an error message.
     */
    @PatchMapping("/patch/{id}")
    public ResponseEntity<?> patchTeam (@PathVariable Long id ,@RequestBody TeamDto teamDto) {
        return teamServiceImpl.patchTeam(id, teamDto);
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
        return teamServiceImpl.deleteTeam(id);
    }
}
