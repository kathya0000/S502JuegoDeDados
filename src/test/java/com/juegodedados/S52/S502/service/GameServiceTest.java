package com.juegodedados.S52.S502.service;

import com.juegodedados.S52.S502.DTO.GameDTO;
import com.juegodedados.S52.S502.models.GameModel;
import com.juegodedados.S52.S502.models.PlayerModel;
import com.juegodedados.S52.S502.repositories.GameRepository;
import com.juegodedados.S52.S502.repositories.PlayerRepository;
import com.juegodedados.S52.S502.services.GameService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
public class GameServiceTest {
    @Mock
    private GameRepository gameRepository;

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private GameService gameService;

    @Test
    public void testCreateGame() {
        // Given
        Long playerId = 1L;
        PlayerModel playerModel = new PlayerModel();
        GameModel gameModel = new GameModel();
        gameModel.setPlayerModel(playerModel);  // Asegurarse de que playerModel no es null

        when(playerRepository.findById(playerId)).thenReturn(Optional.of(playerModel));
        when(gameRepository.save(any(GameModel.class))).thenReturn(gameModel);

        // When
        GameDTO result = gameService.createGame(playerId);

        // Then
        assertNotNull(result);
        verify(playerRepository).findById(playerId);
        verify(gameRepository).save(any(GameModel.class));
    }


    @Test
    public void testGetGamesByPlayerId() {
        // Given
        Long playerId = 1L;
        PlayerModel playerModel = new PlayerModel();
        playerModel.setId(playerId);  // Asegurarse de que PlayerModel tiene un ID

        GameModel gameModel = new GameModel();
        gameModel.setPlayerModel(playerModel);

        List<GameModel> gameModels = Collections.singletonList(gameModel);

        when(playerRepository.findById(playerId)).thenReturn(Optional.of(playerModel));
        when(gameRepository.findByPlayerModelId(playerId)).thenReturn(gameModels);  // Asegúrate de que este método existe y se usa correctamente

        // When
        List<GameDTO> result = gameService.getGamesByPlayerId(playerId);

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(playerRepository).findById(playerId);
        verify(gameRepository).findByPlayerModelId(playerId);  // Asegúrate de que este método existe y se usa correctamente
    }



    @Test
    public void testDeleteGamesByPlayerId() {
        // Given
        Long playerId = 1L;
        PlayerModel playerModel = new PlayerModel();

        when(playerRepository.findById(playerId)).thenReturn(Optional.of(playerModel));

        // When
        gameService.deleteGamesByPlayerId(playerId);

        // Then
        verify(playerRepository).findById(playerId);
        verify(gameRepository).deleteByPlayerModelId(playerId);
    }

    // Helper method for converting GameModel to GameDTO
    private GameDTO convertToDTO(GameModel gameModel) {
        GameDTO gameDTO = new GameDTO();
        gameDTO.setId(gameModel.getId());
        gameDTO.setDado1(gameModel.getDado1());
        gameDTO.setDado2(gameModel.getDado2());
        gameDTO.setWin(gameModel.isWin());
        gameDTO.setPlayerId(gameModel.getPlayerModel().getId());
        return gameDTO;
    }
}








