package com.tomazbr9.tomtech.utils;

import lombok.NoArgsConstructor;

import java.text.Normalizer;

@NoArgsConstructor
public final class SlugUtils {

    private static final int SLUG_MAX_LENGTH = 120;

    public static String generateSlug(String slug){
        String normalized = Normalizer.normalize(slug, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")
                .trim()
                .replaceAll("\\s+", "-")
                .replaceAll("-{2,}", "-");

        return normalized.length() > SLUG_MAX_LENGTH ?
                normalized.substring(0, SLUG_MAX_LENGTH)
                : normalized;
    }
}
