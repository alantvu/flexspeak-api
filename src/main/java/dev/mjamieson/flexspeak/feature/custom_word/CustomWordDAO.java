package dev.mjamieson.flexspeak.feature.custom_word;

import dev.mjamieson.flexspeak.annotation.CurrentUsername;

import java.util.List;

public interface CustomWordDAO {
    void save(@CurrentUsername String username, CustomWordDTO customWordDTO);

    List<CustomWordDTO> get(@CurrentUsername String username);
}
