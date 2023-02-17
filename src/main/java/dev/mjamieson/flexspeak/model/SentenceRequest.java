package dev.mjamieson.flexspeak.model;

import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

public record SentenceRequest(@Getter List<Word> words, String sentence) {
    @SuppressWarnings("unused")
    public SentenceRequest(List<Word> words) {
        this(words, concatenateWords(words));
    }

    private static String concatenateWords(List<Word> words) {
        return words.stream()
                .map(Word::word)
                .collect(Collectors.joining(" "));
    }
}
