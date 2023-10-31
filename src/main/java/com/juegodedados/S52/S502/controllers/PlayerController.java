package com.juegodedados.S52.S502.controllers;

import com.juegodedados.S52.S502.DTO.GameDTO;
import com.juegodedados.S52.S502.DTO.PlayerDTO;
import com.juegodedados.S52.S502.services.PlayerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/players")
@AllArgsConstructor
public class PlayerController {
   @Autowired
    private final PlayerService playerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlayerDTO createPlayer(@Valid @RequestBody PlayerDTO playerDTO) {
       PlayerDTO createdPlayer = playerService.createPlayer(playerDTO);
        return playerService.createPlayer(playerDTO);
    }

    @PutMapping("/{id}")
    public PlayerDTO updatePlayerName(@PathVariable Long id, @Valid @RequestBody PlayerDTO playerDTO) {
        return playerService.updatePlayerName(id, playerDTO.getName());
    }

    @PostMapping("/{id}/games")
    public GameDTO playGame(@PathVariable Long id) {
        return playerService.createGameForPlayer(id);
    }

    @DeleteMapping("/{id}/games")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGamesForPlayer(@PathVariable Long id) {
        playerService.deleteGamesForPlayer(id);
    }

    @GetMapping
    public List<PlayerDTO> getAllPlayers() {
        return playerService.getAllPlayersWithSuccessRate() ;
    }

    @GetMapping("/{id}/games")
    public List<GameDTO> getGamesForPlayer(@PathVariable Long id) {
        return playerService.getGamesForPlayer(id) ;
    }

    @GetMapping("/ranking")
    public Double getAverageRanking() {
        return playerService.getAverageRanking();
    }

    @GetMapping("/ranking/loser")
    public PlayerDTO getLoserPlayer() {
        return playerService.getLoserPlayer();
    }

    @GetMapping("/ranking/winner")
    public PlayerDTO getWinnerPlayer() {
        return playerService.getWinnerPlayer();
    }
}