package dev.mjamieson.flexspeak.feature.custom_aac;


import dev.mjamieson.flexspeak.annotation.CurrentUsername;
import dev.mjamieson.flexspeak.feature.model.Sentence;

public interface CustomAACService {
    Sentence post(@CurrentUsername String username, Sentence sentence);
}
