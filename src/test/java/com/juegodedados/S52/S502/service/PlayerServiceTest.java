package com.juegodedados.S52.S502.service;


import com.juegodedados.S52.S502.DTO.GameDTO;
import com.juegodedados.S52.S502.DTO.PlayerDTO;
import com.juegodedados.S52.S502.exceptions.PlayerNotFoundException;
import com.juegodedados.S52.S502.models.GameModel;
import com.juegodedados.S52.S502.models.PlayerModel;
import com.juegodedados.S52.S502.repositories.GameRepository;
import com.juegodedados.S52.S502.repositories.PlayerRepository;
import com.juegodedados.S52.S502.services.PlayerService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

    public class PlayerServiceTest {

        @Mock
        private PlayerRepository playerRepository;
        @Mock
        private GameRepository gameRepository;

        @InjectMocks
        private PlayerService playerService;

        public PlayerServiceTest() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        public void testSavePlayer() {
            // Crear un PlayerDTO de prueba
            PlayerDTO playerDTO = new PlayerDTO();
            playerDTO.setName("Test Player");

            // Crear un PlayerModel de prueba
            PlayerModel playerModel = new PlayerModel();
            playerModel.setName("Test Player");

            // Configurar el mock para devolver el playerModel cuando save() es llamado
            when(playerRepository.save(any(PlayerModel.class))).thenReturn(playerModel);

            // Llamar al método bajo prueba
            PlayerDTO savedPlayerDTO = playerService.savePlayer(playerDTO);

            // Verificar que save() fue llamado en el mock
            verify(playerRepository).save(any(PlayerModel.class));

            // Asegurarse de que el método devuelve un DTO con el nombre correcto
            assertEquals("Test Player", savedPlayerDTO.getName());
        }
        @Test
        public void testFindPlayerByName() {
            // Crear un PlayerModel de prueba
            PlayerModel playerModel = new PlayerModel();
            playerModel.setName("Test Player");

            // Configurar el mock para devolver el playerModel cuando findByName() es llamado
            when(playerRepository.findByName("Test Player")).thenReturn(Optional.of(playerModel));

            // Llamar al método bajo prueba
            Optional<PlayerDTO> foundPlayerDTO = playerService.findPlayerByName("Test Player");

            // Verificar que findByName() fue llamado en el mock
            verify(playerRepository).findByName("Test Player");

            // Asegurarse de que el método devuelve un DTO con el nombre correcto
            assertTrue(foundPlayerDTO.isPresent());
            assertEquals("Test Player", foundPlayerDTO.get().getName());
        }
        @Test
        public void testCreateGameForPlayer() {
            // Crear un PlayerModel de prueba
            PlayerModel playerModel = new PlayerModel();
            playerModel.setName("Test Player");

            // Crear un GameModel de prueba
            GameModel gameModel = new GameModel();
            gameModel.setPlayerModel(playerModel);
            gameModel.setWin(true);

            // Configurar los mocks
            when(playerRepository.findById(1L)).thenReturn(Optional.of(playerModel));
            when(gameRepository.save(any(GameModel.class))).thenReturn(gameModel);

            // Llamar al método bajo prueba
            GameDTO gameDTO = playerService.createGameForPlayer(1L);

            // Verificar que los métodos fueron llamados en los mocks
            verify(playerRepository).findById(1L);
            verify(gameRepository).save(any(GameModel.class));

            // Asegurarse de que el método devuelve un DTO con los datos correctos
            assertTrue(gameDTO.isWin());
        }
        @Test
        public void testGetAverageRanking() {
            // Crear algunos PlayerModel de prueba
            PlayerModel player1 = new PlayerModel();
            PlayerModel player2 = new PlayerModel();

            // Crear algunos GameModel de prueba
            GameModel game1 = new GameModel();
            game1.setWin(true);
            GameModel game2 = new GameModel();
            game2.setWin(false);

            // Asignar los juegos a los jugadores
            player1.setGames(Arrays.asList(game1, game2));
            player2.setGames(Arrays.asList(game1, game2));

            // Configurar los mocks
            when(playerRepository.findAll()).thenReturn(Arrays.asList(player1, player2));

            // Llamar al método bajo prueba
            Double averageRanking = playerService.getAverageRanking();

            // Verificar que findAll() fue llamado en el mock
            verify(playerRepository).findAll();

            // Asegurarse de que el método devuelve el ranking medio correcto
            assertEquals(50.0, averageRanking, 0.001);
        }
        @Test
        public void testUpdatePlayerName() {
            // Crear un PlayerModel de prueba
            PlayerModel playerModel = new PlayerModel();
            playerModel.setId(1L);
            playerModel.setName("Old Name");

            // Configurar el mock para devolver el playerModel cuando findById() y save() son llamados
            when(playerRepository.findById(1L)).thenReturn(Optional.of(playerModel));
            when(playerRepository.save(any(PlayerModel.class))).thenReturn(playerModel);

            // Llamar al método bajo prueba
            PlayerDTO updatedPlayerDTO = playerService.updatePlayerName(1L, "New Name");

            // Verificar que findById() y save() fueron llamados en el mock
            verify(playerRepository).findById(1L);
            verify(playerRepository).save(any(PlayerModel.class));

            // Asegurarse de que el método devuelve un DTO con el nombre correcto
            assertEquals("New Name", updatedPlayerDTO.getName());
        }
        @Test
        public void testDeleteGamesForPlayer() {
            // Crear un PlayerModel de prueba
            PlayerModel playerModel = new PlayerModel();
            playerModel.setId(1L);
            playerModel.setName("Test Player");

            // Configurar el mock para devolver el playerModel cuando findById() es llamado
            when(playerRepository.findById(1L)).thenReturn(Optional.of(playerModel));

            // Llamar al método bajo prueba
            playerService.deleteGamesForPlayer(1L);

            // Verificar que findById() y deleteAll() fueron llamados en el mock
            verify(playerRepository).findById(1L);
            verify(gameRepository).deleteAll(any());
        }
        @Test
        public void testGetAllPlayersWithSuccessRate() {
            // Crear algunos PlayerModel de prueba
            PlayerModel player1 = new PlayerModel();
            PlayerModel player2 = new PlayerModel();

            // Configurar el mock para devolver los playerModel cuando findAll() es llamado
            when(playerRepository.findAll()).thenReturn(Arrays.asList(player1, player2));

            // Llamar al método bajo prueba
            List<PlayerDTO> playerDTOs = playerService.getAllPlayersWithSuccessRate();

            // Verificar que findAll() fue llamado en el mock
            verify(playerRepository).findAll();

            // Asegurarse de que el método devuelve una lista del tamaño correcto
            assertEquals(2, playerDTOs.size());
        }
        @Test
        public void testGetTopPlayer() {
            // Crear un PlayerModel de prueba
            PlayerModel playerModel = new PlayerModel();
            playerModel.setName("Old Name");

            // Configurar el mock para devolver el playerModel cuando findById() es llamado
            when(playerRepository.findById(1L)).thenReturn(Optional.of(playerModel));

            // Configurar el mock para devolver el playerModel cuando save() es llamado
            when(playerRepository.save(any(PlayerModel.class))).thenReturn(playerModel);

            // Llamar al método bajo prueba
            PlayerDTO updatedPlayerDTO = playerService.updatePlayerName(1L, "New Name");

            // Verificar que findById() y save() fueron llamados en el mock
            verify(playerRepository).findById(1L);
            verify(playerRepository).save(any(PlayerModel.class));

            // Asegurarse de que el método devuelve un DTO con el nombre actualizado
            assertNotNull(updatedPlayerDTO);  // Asegurarse de que no es null
            assertEquals("New Name", updatedPlayerDTO.getName());
        }
        @Test
        public void testGetGamesForPlayerWhenPlayerNotFound() {
            // Configurar el mock para devolver un Optional vacío cuando findById() es llamado
            when(playerRepository.findById(1L)).thenReturn(Optional.empty());

            // Esperar que se lance una excepción cuando se llama al método bajo prueba
            assertThrows(PlayerNotFoundException.class, () -> {
                playerService.getGamesForPlayer(1L);
            });

            // Verificar que findById() fue llamado en el mock
            verify(playerRepository).findById(1L);
        }

    }


