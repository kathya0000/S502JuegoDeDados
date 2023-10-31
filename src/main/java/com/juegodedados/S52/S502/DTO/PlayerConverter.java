package com.juegodedados.S52.S502.DTO;


import com.juegodedados.S52.S502.models.GameModel;
import com.juegodedados.S52.S502.models.PlayerModel;

import java.util.List;

public class PlayerConverter {
    public static PlayerDTO convertToDTO(PlayerModel playerModel) {
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setId(playerModel.getId());
        playerDTO.setName(playerModel.getName());
        playerDTO.setPassword(playerModel.getPassword());
        playerDTO.setRegistrationDate(playerModel.getRegistrationDate());
        return playerDTO;
    }

    public static PlayerModel convertToEntity(PlayerDTO playerDTO) {
        PlayerModel playerModel = new PlayerModel();
        playerModel.setName(playerDTO.getName());
        playerModel.setPassword(playerDTO.getPassword());
        playerModel.setRegistrationDate(playerDTO.getRegistrationDate());
        return playerModel;
    }
    public static PlayerDTO convertToDTOWithSuccessRate(PlayerModel playerModel) {
        PlayerDTO playerDTO = convertToDTO(playerModel);
        double successRate = calculateSuccessRate(playerModel);
        playerDTO.setSuccessRate(successRate);
        return playerDTO;
    }

    private static double calculateSuccessRate(PlayerModel playerModel) {
        List<GameModel> games = playerModel.getGames();
        if (games == null || games.isEmpty()) {
            return 0;
        }
        long totalGames = games.size();
        long wonGames = games.stream().filter(GameModel::isWin).count();
        return ((double) wonGames / totalGames) * 100;
    }
}