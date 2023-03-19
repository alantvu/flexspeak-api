package dev.mjamieson.flexspeak.feature.custom_word;

public record CustomWordRequest(
        String wordToDisplay,
        String wordToSpeak,
        String imagePath,
        Integer gridRow,
        Integer gridColumn,
        GridTitleEnum gridTitleEnum
        ) {
}
