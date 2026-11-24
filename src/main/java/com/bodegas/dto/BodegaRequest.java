package com.bodegas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BodegaRequest(
        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 100, message = "El nombre no puede superar 100 caracteres")
        String nombre,
        @NotBlank(message = "La ubicacion es obligatoria")
        @Size(max = 150, message = "La ubicacion no puede superar 150 caracteres")
        String ubicacion) {
}
