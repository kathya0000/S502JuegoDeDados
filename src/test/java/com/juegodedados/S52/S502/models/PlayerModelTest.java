package com.juegodedados.S52.S502.models;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

public class PlayerModelTest {

    @Test
    public void testConstructorAssignsAnonimoWhenNameIsNull() {
        PlayerModel player = new PlayerModel(null);
        assertEquals("ANONIMO", player.getName());
        assertNotNull(player.getRegistrationDate());
    }

    @Test
    public void testConstructorAssignsAnonimoWhenNameIsEmpty() {
        PlayerModel player = new PlayerModel("");
        assertEquals("ANONIMO", player.getName());
        assertNotNull(player.getRegistrationDate());
    }

    @Test
    public void testConstructorAssignsGivenNameWhenNameIsNotEmpty() {
        PlayerModel player = new PlayerModel("John");
        assertEquals("John", player.getName());
        assertNotNull(player.getRegistrationDate());
    }
}
