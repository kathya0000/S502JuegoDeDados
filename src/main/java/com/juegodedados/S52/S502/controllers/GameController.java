package com.juegodedados.S52.S502.controllers;

import com.juegodedados.S52.S502.DTO.GameDTO;
import com.juegodedados.S52.S502.services.GameService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/players/{playerId}/games")
public class GameController {

   @Autowired
    private final GameService gameService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GameDTO createGame(@PathVariable Long playerId) {
        GameDTO newGame = gameService.createGame(playerId);
        return gameService.createGame(playerId);
    }

    @GetMapping
    public List<GameDTO> getGamesByPlayerId(@PathVariable Long playerId) {
        List<GameDTO> games = gameService.getGamesByPlayerId(playerId);
        return gameService.getGamesByPlayerId(playerId);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGamesByPlayerId(@PathVariable Long playerId) {
        gameService.deleteGamesByPlayerId(playerId);
    }
}
