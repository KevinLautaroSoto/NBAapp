package com.lautaro.NbaApp.Service;

import com.lautaro.NbaApp.Controller.Dto.TeamDto;
import org.springframework.http.ResponseEntity;

public interface TeamService {

    ResponseEntity<String> createTeam(TeamDto teamDto);

    ResponseEntity<?> getAllTeam();

    ResponseEntity<?> getTeamById(Long id);

    ResponseEntity<?> getTeamByName(String name);

    ResponseEntity<?> updateTeam(Long id, TeamDto teamDto);

    ResponseEntity<?> patchTeam (Long id, TeamDto teamDto);

    ResponseEntity<String> deleteTeam (Long id);
}
