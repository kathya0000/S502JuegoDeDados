package com.juegodedados.S52.S502.DTO;

import com.juegodedados.S52.S502.models.GameModel;

public class GameConverter {
    public static GameDTO convertToDTO(GameModel gameModel) {
        GameDTO gameDTO = new GameDTO();
        gameDTO.setId(gameModel.getId());
        gameDTO.setDado1(gameModel.getDado1());
        gameDTO.setDado2(gameModel.getDado2());
        gameDTO.setWin(gameModel.isWin());

        // Verificar si PlayerModel es null antes de intentar acceder a sus métodos/atributos
        if(gameModel.getPlayerModel() != null) {
            gameDTO.setPlayerId(gameModel.getPlayerModel().getId());
        } else {
            // Manejar el caso en que PlayerModel es null, si es necesario
            // Por ejemplo, podrías establecer el playerId en null o lanzar una excepción, según tus requisitos
            gameDTO.setPlayerId(null);
        }
        return gameDTO;
    }


    public static GameModel convertToEntity(GameDTO gameDTO) {
        GameModel gameModel = new GameModel();
        gameModel.setId(gameDTO.getId());
        gameModel.setDado1(gameDTO.getDado1());
        gameModel.setDado2(gameDTO.getDado2());
        gameModel.setWin(gameDTO.isWin());
        return gameModel;
    }
}






