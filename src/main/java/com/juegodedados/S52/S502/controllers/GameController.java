package com.juegodedados.S52.S502.controllers;

import com.juegodedados.S52.S502.DTO.GameDTO;
import com.juegodedados.S52.S502.exceptions.PlayerNotFoundException;
import com.juegodedados.S52.S502.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/players/{playerId}/games")
public class GameController {

    private final GameService gameService;

    @Autowired
    private GameController(GameService gameService){
        this.gameService = gameService;
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<GameDTO> createGame(@PathVariable Long playerId) {
        GameDTO newGame = gameService.createGame(playerId);
        return new ResponseEntity<>(newGame, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<GameDTO>> getGamesByPlayerId(@PathVariable Long playerId) {
        List<GameDTO> games = gameService.getGamesByPlayerId(playerId);
        return new ResponseEntity<>(games, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteGamesByPlayerId(@PathVariable Long playerId) {
        gameService.deleteGamesByPlayerId(playerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
