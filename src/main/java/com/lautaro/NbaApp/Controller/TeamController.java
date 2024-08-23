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

    @PostMapping
    public void createTeam (@RequestBody TeamDto teamDto) {
        teamService.createProduct(teamDto);
    }

    @GetMapping
    public List<Team> getAllTeam () {
        return teamService.getAllTeam();
    }

    @GetMapping("/{id}")
    public Optional<Team> getTeamById (@PathVariable Long id) {
        return teamService.getTeamById(id);
    }

    @PutMapping("/{id}")
    public Team updateTeam (@PathVariable Long id, @RequestBody TeamDto teamDto) {
        return teamService.updateTeam(id, teamDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTeam (@PathVariable Long id) {
        return teamService.deleteTeam(id);
    }
}
