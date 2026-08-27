package com.tomazbr9.tomtech.dto.post;

import com.tomazbr9.tomtech.enums.PostStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CreatePostRequest(
        @NotBlank(message = "Titulo do artigo é obrigatório")
        @Min(value = 5, message = "Titulo do artigo deve ter  no minimo 5 caracters")
        String title,

        @NotBlank(message = "Resumo do artigo é obrigatório")
        String summary,

        @NotBlank(message = "Conteudo do artigo é obrigatório")
        String content,

        @NotBlank(message = "Status do artigo é obrigatório")
        PostStatus status,

        @NotNull(message = "Categoria é obrigatória")
        UUID categoryId
) {
}
