package dev.mjamieson.flexspeak.feature.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public record Sentence(List<Word> words, String sentence) {
//    @SuppressWarnings("unused")
//    public Sentence(List<Word> words) {
//        this(words, concatenateWords(words));
//    }
//
//    private static String concatenateWords(List<Word> words) {
//        return words.stream()
//                .map(Word::word)
//                .collect(Collectors.joining(" "));
//    }
}
