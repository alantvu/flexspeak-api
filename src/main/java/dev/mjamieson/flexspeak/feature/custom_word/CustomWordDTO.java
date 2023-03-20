package dev.mjamieson.flexspeak.feature.custom_word;

import java.util.List;
import java.util.stream.Collectors;

public record CustomWordDTO(
        String wordToDisplay,
        String wordToSpeak,
        String imagePath,
        Integer gridRow,
        Integer gridColumn,
        GridTitleEnum gridTitleEnum
) {
    public static CustomWordDTO from(CustomWord customWord) {
        return new CustomWordDTO(
                customWord.getWordToDisplay(),
                customWord.getWordToSpeak(),
                customWord.getImagePath(),
                customWord.getGridRow(),
                customWord.getGridColumn(),
                customWord.getGridTitleEnum()
        );
    }
    public static List<CustomWordDTO> from(List<CustomWord> customWords) {
        return customWords.stream().map(CustomWordDTO::from).collect(Collectors.toList());
    }
}
