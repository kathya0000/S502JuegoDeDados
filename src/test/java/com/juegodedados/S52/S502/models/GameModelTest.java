package com.juegodedados.S52.S502.models;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class GameModelTest {

    @Test
    public void testLanzarDadosAssignsRandomValuesToDados() {
        GameModel game = new GameModel();
        game.lanzarDados();
        assertTrue(game.getDado1() >= 1 && game.getDado1() <= 6);
        assertTrue(game.getDado2() >= 1 && game.getDado2() <= 6);
    }

    @Test
    public void testLanzarDadosAssignsWinCorrectly() {
        GameModel game = new GameModel();
        game.lanzarDados();
        assertEquals(game.isWin(), game.getDado1() + game.getDado2() == 7);
    }
}
