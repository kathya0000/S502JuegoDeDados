package com.juegodedados.S52.S502.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class PlayerDTO {
    private Long id;

    @NotNull(message = "El nombre no puede ser nulo")
    @Size(min = 1, max = 20, message = "El nombre debe tener entre 1 y 20 caracteres")
    private String name;

    @Column(nullable = false)
    @NotBlank(message = "La contraseña no puede estar vacía")
    private String password;

    private LocalDateTime registrationDate;
    private double successRate;


}
