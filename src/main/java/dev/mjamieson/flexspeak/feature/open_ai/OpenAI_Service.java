package dev.mjamieson.flexspeak.feature.open_ai;


import dev.mjamieson.flexspeak.annotation.CurrentUsername;
import dev.mjamieson.flexspeak.feature.model.Sentence;

public interface OpenAI_Service {
    Sentence postSpeech(@CurrentUsername String username, Sentence sentence);
}
