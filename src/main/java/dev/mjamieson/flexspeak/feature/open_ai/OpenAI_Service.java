package dev.mjamieson.flexspeak.feature.open_ai;


import dev.mjamieson.flexspeak.annotation.CurrentUsername;
import dev.mjamieson.flexspeak.feature.model.Sentence;

import java.util.List;

public interface OpenAI_Service {
    Sentence postSpeech(@CurrentUsername String username, Sentence sentence);
    List<OpenAI_SuggestionsDTO> postSuggestion(@CurrentUsername String username, List<OpenAI_SuggestionsDTO> openAI_suggestionsDTOS);
}
