package com.lautaro.NbaApp.Utilities;

import com.lautaro.NbaApp.Controller.Dto.TeamDto;
import com.lautaro.NbaApp.Models.Team;

public class TeamMapper {

    public static Team mapToTeam (Team teamMapped, TeamDto teamDto) {
        if (teamDto == null) {
            return null;
        }

        teamMapped.setName(teamDto.getName());
        teamMapped.setCity(teamDto.getCity());
        teamMapped.setConference(teamDto.getConference());
        teamMapped.setDivision(teamDto.getDivision());
        teamMapped.setAbbreviation(teamDto.getAbbreviation());
        teamMapped.setFull_name(teamDto.getFull_name());

        return teamMapped;
    }
}
