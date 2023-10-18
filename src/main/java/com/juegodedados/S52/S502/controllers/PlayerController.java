package com.juegodedados.S52.S502.controllers;

import com.juegodedados.S52.S502.DTO.GameDTO;
import com.juegodedados.S52.S502.DTO.PlayerDTO;
import com.juegodedados.S52.S502.services.PlayerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/players")
public class PlayerController {
    @Autowired
    private final PlayerService playerService;

    @Autowired
    private PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping
    public ResponseEntity<PlayerDTO> createPlayer(@Valid @RequestBody PlayerDTO playerDTO) {
       PlayerDTO createdPlayer = playerService.createPlayer(playerDTO);
        return new ResponseEntity<>(createdPlayer, HttpStatus.CREATED);
    }



    @PutMapping("/{id}")
    public ResponseEntity<PlayerDTO> updatePlayerName(@PathVariable Long id, @Valid @RequestBody PlayerDTO playerDTO) {
        PlayerDTO updatedPlayer = playerService.updatePlayerName(id, playerDTO.getName());
        return new ResponseEntity<>(updatedPlayer, HttpStatus.OK);
    }

    @PostMapping("/{id}/games")
    public ResponseEntity<GameDTO> playGame(@PathVariable Long id) {
        GameDTO gameResult = playerService.createGameForPlayer(id);
        return new ResponseEntity<>(gameResult, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}/games")
    public ResponseEntity<Void> deleteGamesForPlayer(@PathVariable Long id) {
        playerService.deleteGamesForPlayer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<PlayerDTO>> getAllPlayers() {
        List<PlayerDTO> players = playerService.getAllPlayersWithSuccessRate();
        return new ResponseEntity<>(players, HttpStatus.OK);
    }

    @GetMapping("/{id}/games")
    public ResponseEntity<List<GameDTO>> getGamesForPlayer(@PathVariable Long id) {
        List<GameDTO> games = playerService.getGamesForPlayer(id);
        return new ResponseEntity<>(games, HttpStatus.OK);
    }

    @GetMapping("/ranking")
    public ResponseEntity<Double> getAverageRanking() {
        Double averageRanking = playerService.getAverageRanking();
        return new ResponseEntity<>(averageRanking, HttpStatus.OK);
    }

    @GetMapping("/ranking/loser")
    public ResponseEntity<PlayerDTO> getLoserPlayer() {
        PlayerDTO loserPlayer = playerService.getLoserPlayer();
        return new ResponseEntity<>(loserPlayer, HttpStatus.OK);
    }

    @GetMapping("/ranking/winner")
    public ResponseEntity<PlayerDTO> getWinnerPlayer() {
        PlayerDTO winnerPlayer = playerService.getWinnerPlayer();
        return new ResponseEntity<>(winnerPlayer, HttpStatus.OK);
    }
}