package dev.mjamieson.flexspeak.feature.aac;

import dev.mjamieson.flexspeak.annotation.CurrentUsername;
import dev.mjamieson.flexspeak.feature.model.SentenceRequest;

public interface AAC_Service {
    Void postSentence(@CurrentUsername String username, SentenceRequest firstClass);
}
