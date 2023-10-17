package com.juegodedados.S52.S502.services;

import com.juegodedados.S52.S502.DTO.GameConverter;
import com.juegodedados.S52.S502.DTO.GameDTO;
import com.juegodedados.S52.S502.DTO.PlayerConverter;
import com.juegodedados.S52.S502.DTO.PlayerDTO;
import com.juegodedados.S52.S502.exceptions.PlayerNotFoundException;
import com.juegodedados.S52.S502.models.GameModel;
import com.juegodedados.S52.S502.models.PlayerModel;
import com.juegodedados.S52.S502.repositories.GameRepository;
import com.juegodedados.S52.S502.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.juegodedados.S52.S502.DTO.PlayerConverter.convertToDTO;

@Service
public class PlayerService {

    @Autowired
    private final PlayerRepository playerRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private final GameRepository gameRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository, GameRepository gameRepository){
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
    }
    public PlayerDTO savePlayer(PlayerDTO playerDTO){
        PlayerModel playerModel = PlayerConverter.convertToEntity(playerDTO);
        playerModel = playerRepository.save(playerModel);
        return convertToDTO(playerModel);
    }
    public Optional<PlayerDTO> findPlayerByName(String name){
        Optional<PlayerModel> playerModelOptional = playerRepository.findByName(name);
        return playerModelOptional.map(PlayerConverter::convertToDTO);
    }
    public Optional<PlayerDTO> findPlayerById(Long playerId) {
        Optional<PlayerModel> playerModelOptional = playerRepository.findById(playerId);
        return playerModelOptional.map(PlayerConverter::convertToDTO);
    }

   /* public PlayerDTO createPlayer(String name){
        PlayerModel playerModel = new PlayerModel(name);
        playerModel = playerRepository.save(playerModel);
        return convertToDTO(playerModel);
    }*/
   public PlayerDTO createPlayer(PlayerDTO playerDTO) {
       String hashedPassword = passwordEncoder.encode(playerDTO.getPassword());// Codificar la contraseña antes de guardarla

       // Convertir DTO a modelo
       PlayerModel playerModel = PlayerConverter.convertToEntity(playerDTO);
       playerModel.setPassword(hashedPassword); // Establecer la contraseña encriptada

       playerRepository.save(playerModel);
       // Convertir el modelo guardado de nuevo a DTO para devolverlo (sin la contraseña)
       return PlayerConverter.convertToDTO(playerModel);
   }

    public PlayerDTO updatePlayerName(Long playerId, String newName){
        PlayerModel playerModel = playerRepository.findById(playerId)
                .orElseThrow(() -> new PlayerNotFoundException("Player no encontrado"));
        playerModel.setName(newName);
        playerModel = playerRepository.save(playerModel);
        return convertToDTO(playerModel);
    }
    public GameDTO createGameForPlayer(Long playerId){
        PlayerModel playerModel = playerRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Player not found"));
        GameModel gameModel = new GameModel();
        gameModel.lanzarDados();
        gameModel.setPlayerModel(playerModel);
        gameModel = gameRepository.save(gameModel);
        return GameConverter.convertToDTO(gameModel); // Asumiendo que tienes un GameConverter similar
    }
    public void deleteGamesForPlayer(Long playerId){
        PlayerModel playerModel = playerRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Player not found"));
        gameRepository.deleteAll(playerModel.getGames());
        playerRepository.delete(playerModel);//  Si deseas también eliminar el jugador
    }
    public List<PlayerDTO> getAllPlayersWithSuccessRate(){
        List<PlayerModel> playerModels = playerRepository.findAll();
        return playerModels.stream()
                .map(PlayerConverter::convertToDTOWithSuccessRate) // Necesitarás agregar este método en PlayerConverter
                .collect(Collectors.toList());
    }
    public List<GameDTO> getGamesForPlayer(Long playerId) throws PlayerNotFoundException {
        Optional<PlayerModel> playerOptional = playerRepository.findById(playerId);
        if(playerOptional.isPresent()){
            List<GameModel> games = playerOptional.get().getGames();
            return games.stream().map(GameConverter::convertToDTO) // Asumiendo que tienes un GameConverter similar
                    .collect(Collectors.toList());
        } else {
            throw new PlayerNotFoundException("Player with ID " + playerId + " not found");
        }
    }

    public Double getAverageRanking(){
        List<PlayerModel> players = playerRepository.findAll();
        return players.stream()
                .mapToDouble(this::calculateSuccessRate) // Este método puede permanecer en PlayerService
                .average()
                .orElse(0.0);
    }

    public PlayerDTO getLoserPlayer(){
        List<PlayerModel> players = playerRepository.findAll();
        return players.stream()
                .min(Comparator.comparing(this::calculateSuccessRate))
                .map(PlayerConverter::convertToDTO)
                .orElse(null);
    }

    public PlayerDTO getWinnerPlayer(){
        List<PlayerModel> players = playerRepository.findAll();
        return players.stream()
                .max(Comparator.comparing(this::calculateSuccessRate))
                .map(PlayerConverter::convertToDTO)
                .orElse(null);
    }

    private double calculateSuccessRate(PlayerModel player){
        List<GameModel> games = player.getGames();
        long winCount = games.stream().filter(GameModel::isWin).count();
        return (games.size() > 0) ? ((double) winCount / games.size()) * 100 : 0;
    }

    }



