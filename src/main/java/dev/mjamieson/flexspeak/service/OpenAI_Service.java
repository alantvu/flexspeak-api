package dev.mjamieson.flexspeak.service;


import dev.mjamieson.flexspeak.annotation.CurrentUsername;
import dev.mjamieson.flexspeak.model.SentenceRequest;
import dev.mjamieson.flexspeak.model.SentenceResponse;

public interface OpenAI_Service {
    SentenceResponse post(@CurrentUsername String username, SentenceRequest firstClass);
}
