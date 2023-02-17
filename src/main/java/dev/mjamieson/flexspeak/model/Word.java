package dev.mjamieson.flexspeak.model;


import java.time.LocalDateTime;

public record Word(
        String word,
        LocalDateTime timestamp
) {
}
