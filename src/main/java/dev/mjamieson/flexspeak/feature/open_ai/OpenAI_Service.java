package dev.mjamieson.flexspeak.feature.open_ai;


import dev.mjamieson.flexspeak.annotation.CurrentUsername;
import dev.mjamieson.flexspeak.feature.model.SentenceRequest;
import dev.mjamieson.flexspeak.feature.model.SentenceResponse;

public interface OpenAI_Service {
    SentenceResponse post(@CurrentUsername String username, SentenceRequest firstClass);
}
