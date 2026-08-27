package com.tomazbr9.tomtech.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginUserRequest(

        @Email(message = "Email Inválido")
        String email,

        @NotBlank(message = "Senha inválida")
        String password
) {
}
