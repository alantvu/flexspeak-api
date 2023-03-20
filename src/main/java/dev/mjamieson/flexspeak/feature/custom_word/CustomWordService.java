package dev.mjamieson.flexspeak.feature.custom_word;


import dev.mjamieson.flexspeak.annotation.CurrentUsername;
import dev.mjamieson.flexspeak.feature.model.Sentence;

import java.util.List;

public interface CustomWordService {

    Void post(@CurrentUsername String username, CustomWordRequest customWordRequest);
    List<CustomWord> get(String username);
}
