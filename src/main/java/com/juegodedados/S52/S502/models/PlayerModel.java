package com.juegodedados.S52.S502.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "players")

public class PlayerModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 1, max = 20, message = "El nombre debe tener entre 1 y 20 caracteres")
    private String name;

    @Column(nullable = false)
    @NotBlank(message = "La contraseña no puede estar vacía")
    private String password;


    @Column(columnDefinition = "TIMESTAMP")
    @NotNull(message = "La fecha de registro no puede ser nula")
    private LocalDateTime registrationDate;

    @OneToMany(mappedBy = "playerModel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<GameModel> games = new ArrayList<>();

    public PlayerModel(String name){
        this.name = (name == null || name.trim().isEmpty())? "ANONIMO" : name.trim();
        this.registrationDate = LocalDateTime.now();
        this.password = password;
    }

    public void addGame(GameModel gameModel){
        games.add(gameModel);
        gameModel.setPlayerModel(this);
    }
}
