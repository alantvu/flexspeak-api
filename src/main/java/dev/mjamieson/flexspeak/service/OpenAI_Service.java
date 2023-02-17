package dev.mjamieson.flexspeak.service;


import dev.mjamieson.flexspeak.annotation.CurrentUsername;
import dev.mjamieson.flexspeak.model.FirstClass;

public interface OpenAI_Service {
    FirstClass post(@CurrentUsername String username, FirstClass firstClass);
}
