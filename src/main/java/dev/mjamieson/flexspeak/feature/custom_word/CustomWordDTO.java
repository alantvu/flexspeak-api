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

    public static CustomWordDTO fromWithImagePath(CustomWordDTO customWordDTO, String imagePath) {
        return new CustomWordDTO(
                customWordDTO.wordToDisplay(),
                customWordDTO.wordToSpeak(),
                imagePath,
                customWordDTO.gridRow(),
                customWordDTO.gridColumn(),
                customWordDTO.gridTitleEnum()
        );
    }
    public static List<CustomWordDTO> from(List<CustomWord> customWords) {
        return customWords.stream().map(CustomWordDTO::from).collect(Collectors.toList());
    }
}
