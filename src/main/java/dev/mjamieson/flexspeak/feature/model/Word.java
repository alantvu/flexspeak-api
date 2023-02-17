package dev.mjamieson.flexspeak.feature.model;


import java.time.LocalDateTime;

public record Word(
        String word,
        LocalDateTime timestamp
) {
}
