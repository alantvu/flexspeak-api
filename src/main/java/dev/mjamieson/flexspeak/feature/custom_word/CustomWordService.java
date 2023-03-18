package dev.mjamieson.flexspeak.feature.custom_word;


import dev.mjamieson.flexspeak.annotation.CurrentUsername;
import dev.mjamieson.flexspeak.feature.model.Sentence;

public interface CustomWordService {

    Sentence post(@CurrentUsername String username, CustomWordRequest customWordRequest);
}
