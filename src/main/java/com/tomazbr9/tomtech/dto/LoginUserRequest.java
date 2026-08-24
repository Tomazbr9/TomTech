package com.tomazbr9.tomtech.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginUserRequest(

        @NotBlank(message = "Email inválido")
        String email,

        @NotBlank(message = "Senha inválida")
        String password
) {
}
