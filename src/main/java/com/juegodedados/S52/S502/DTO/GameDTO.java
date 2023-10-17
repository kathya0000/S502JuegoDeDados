package com.juegodedados.S52.S502.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameDTO {
    private Long id;

    @Min(value = 1, message = "El valor del dado debe ser al menos 1")
    @Max(value = 6, message = "El valor del dado no puede ser mayor que 6")
    private int dado1;

    @Min(value = 1, message = "El valor del dado debe ser al menos 1")
    @Max(value = 6, message = "El valor del dado no puede ser mayor que 6")
    private int dado2;

    private boolean win;
    @NotNull(message = "El ID del jugador no puede ser nulo")
    private Long playerId; // Esto asume que cada juego está asociado a un jugador

   /* @NotNull(message = "El ID del jugador no puede ser nulo")
    public Long getPlayerId() {
        return playerId;
    }*/
}
