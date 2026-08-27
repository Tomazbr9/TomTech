package com.tomazbr9.tomtech.dto.category;

import jakarta.validation.constraints.NotBlank;

public record CreateCategoryRequest(
        @NotBlank(message = "Nome da categoria é obrigatório")
        String name,

        @NotBlank(message = "Selecione alguma cor para a categoria")
        String color
) {
}
