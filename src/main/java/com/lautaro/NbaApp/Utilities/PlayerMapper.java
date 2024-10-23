package com.lautaro.NbaApp.Utilities;

import com.lautaro.NbaApp.Controller.Dto.PlayerDto;
import com.lautaro.NbaApp.Models.Player;
import com.lautaro.NbaApp.Service.Impl.TeamServiceImpl;

public class PlayerMapper {
    //Convert a PlayerDto to a Player.

    public static Player mapToPlayer(Player player, PlayerDto playerDto, TeamServiceImpl teamServiceImpl) {
        if (playerDto == null) {
            return null;
        }

        player.setFirstName(playerDto.getFirst_name());
        player.setLastName(playerDto.getLast_name());
        player.setJersey_number(playerDto.getJersey_number());
        player.setPosition(playerDto.getPosition());
        player.setHeight(playerDto.getHeight());
        player.setWeight(playerDto.getWeight());
        player.setCountry(playerDto.getCountry());
        player.setCollege(playerDto.getCollege());
        player.setDraft_year(playerDto.getDraft_year());
        player.setDraft_round(playerDto.getDraft_round());
        player.setDraft_number(playerDto.getDraft_number());
        player.setTeam(playerDto.getTeam());

        return player;
    }
}
