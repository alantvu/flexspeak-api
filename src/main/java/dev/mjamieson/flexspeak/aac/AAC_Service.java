package dev.mjamieson.flexspeak.aac;

import dev.mjamieson.flexspeak.annotation.CurrentUsername;
import dev.mjamieson.flexspeak.model.FirstClass;

public interface AAC_Service {
    Void postSentence(@CurrentUsername String username, FirstClass firstClass);
}
