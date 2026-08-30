package com.tomazbr9.tomtech.dto.post;

import java.time.LocalDateTime;
import java.util.UUID;

public record RecoveryPostResponse(
        UUID id,
        String title,
        String summary,
        String content,
        LocalDateTime createdAt
) {}
