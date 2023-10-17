package com.juegodedados.S52.S502.services;

import com.juegodedados.S52.S502.DTO.GameConverter;
import com.juegodedados.S52.S502.DTO.GameDTO;
import com.juegodedados.S52.S502.DTO.PlayerConverter;
import com.juegodedados.S52.S502.DTO.PlayerDTO;
import com.juegodedados.S52.S502.exceptions.PlayerNotFoundException;
import com.juegodedados.S52.S502.models.GameModel;
import com.juegodedados.S52.S502.models.PlayerModel;
import com.juegodedados.S52.S502.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameService {

    @Autowired
    private final GameRepository gameRepository;

    @Autowired
    private final PlayerService playerService; // Usamos PlayerService en lugar de PlayerRepository

    @Autowired
    public GameService(GameRepository gameRepository, PlayerService playerService){
        this.gameRepository = gameRepository;
        this.playerService = playerService;
    }

    public List<GameDTO> getGamesByPlayerId(Long playerId) {

        playerService.findPlayerById(playerId)
                .orElseThrow(() -> new PlayerNotFoundException("Player not found with id: " + playerId));

        List<GameModel> games = gameRepository.findByPlayerModelId(playerId);

        return games.stream().map(GameConverter::convertToDTO).collect(Collectors.toList());
    }

    public GameDTO createGame(Long playerId) {

        PlayerDTO playerDTO = playerService.findPlayerById(playerId)
                .orElseThrow(() -> new PlayerNotFoundException("Player not found with id: " + playerId));
        PlayerModel playerModel = PlayerConverter.convertToEntity(playerDTO);
        GameModel gameModel = new GameModel();
        gameModel.setPlayerModel(playerModel);
        gameModel.lanzarDados();
        gameModel = gameRepository.save(gameModel);// Guardamos el juego en la base de datos
        return GameConverter.convertToDTO(gameModel);
    }



    public void deleteGamesByPlayerId(Long playerId) {

        playerService.findPlayerById(playerId)
                .orElseThrow(() -> new PlayerNotFoundException("Player not found with id: " + playerId));

        gameRepository.deleteByPlayerModelId(playerId);
    }

}





