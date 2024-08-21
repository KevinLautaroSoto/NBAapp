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
        teamMapped.setPrimaryColor(teamDto.getPrimaryColor());
        teamMapped.setSecondaryColor(teamDto.getSecondaryColor());
        teamMapped.setTerciaryColor(teamDto.getTerciaryColor());
        teamMapped.setLogoUrl(teamDto.getLogoUrl());
        teamMapped.setStadiumName(teamDto.getStadiumName());
        teamMapped.setHeadcoach(teamDto.getHeadcoach());

        return teamMapped;
    }
}
