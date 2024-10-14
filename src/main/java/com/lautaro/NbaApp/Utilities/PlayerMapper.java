package com.lautaro.NbaApp.Utilities;

import com.lautaro.NbaApp.Controller.Dto.PlayerDto;
import com.lautaro.NbaApp.Models.Player;
import com.lautaro.NbaApp.Models.Team;
import com.lautaro.NbaApp.Service.TeamService;

import java.util.NoSuchElementException;

public class PlayerMapper {
    //Convert a PlayerDto to a Player.

    public static Player mapToPlayer(Player player, PlayerDto playerDto, TeamService teamService) {
        if (playerDto == null) {
            return null;
        }

        player.setFirst_name(playerDto.getFirst_name());
        player.setLast_name(playerDto.getLast_name());
        player.setJersey_number(playerDto.getJersey_number());
        player.setPosition(playerDto.getPosition());
        player.setHeight(playerDto.getHeight());
        player.setWeight(playerDto.getWeight());
        player.setCountry(playerDto.getCountry());
        player.setCollege(playerDto.getCollege());
        player.setDraft_year(playerDto.getDraft_year());
        player.setDraft_round(playerDto.getDraft_round());
        player.setDraft_number(playerDto.getDraft_number());

        Team teamSearched = teamService.getTeamById(playerDto.getTeamId())
                        .orElseThrow(() -> new NoSuchElementException("Team not found with ID: " + playerDto.getTeamId()));

        player.setTeam(teamSearched);

        return player;
    }
}
