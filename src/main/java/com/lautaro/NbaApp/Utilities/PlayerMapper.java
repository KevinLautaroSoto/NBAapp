package com.lautaro.NbaApp.Utilities;

import com.lautaro.NbaApp.Controller.Dto.PlayerDto;
import com.lautaro.NbaApp.Models.Player;

public class PlayerMapper {
    //Convert a PlayerDto to a Player.

    public static Player mapToPlayer(PlayerDto playerDto) {
        if (playerDto == null) {
            return null;
        }

        Player player = new Player();

        player.setName(playerDto.getName());
        player.setLastName(playerDto.getLastName());
        player.setJersey(playerDto.getJersey());
        player.setPosition(playerDto.getPosition());
        player.setHeight(playerDto.getHeight());
        player.setWeight(playerDto.getWeight());
        player.setCountry(playerDto.getCountry());

        return player;
    }
}
