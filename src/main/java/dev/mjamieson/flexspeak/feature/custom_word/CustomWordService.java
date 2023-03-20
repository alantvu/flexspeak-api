package dev.mjamieson.flexspeak.feature.custom_word;


import dev.mjamieson.flexspeak.annotation.CurrentUsername;

import java.util.List;

public interface CustomWordService {

    Void post(@CurrentUsername String username, CustomWordDTO customWordDTO);
    List<CustomWordDTO> get(String username);
}
