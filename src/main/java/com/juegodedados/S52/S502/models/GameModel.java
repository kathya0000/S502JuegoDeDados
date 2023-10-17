package com.juegodedados.S52.S502.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name= "game")
public class GameModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Min(1) @Max(6)
    private int dado1;

    @Column(nullable = false)
    @Min(1) @Max(6)
    private int dado2;

    private boolean win;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", nullable = false)
    private PlayerModel playerModel;

    public void lanzarDados(){
        this.dado1 = (int) (Math.random() * 6) + 1;
        this.dado2 = (int) (Math.random() * 6) + 1;
        this.win = (this.dado1 + this.dado2 == 7);

    }
}
