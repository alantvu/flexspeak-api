package dev.mjamieson.flexspeak.controller;

import dev.mjamieson.flexspeak.integration.external.spacex.service.external_api.SpaceXCapsuleService;
import dev.mjamieson.flexspeak.service.OpenAIService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/open_ai")
@RequiredArgsConstructor
public class OpenAiController {
    private final OpenAIService openAIService;
    private final SpaceXCapsuleService spaceXCapsuleService;

    @GetMapping
    public String index() {
//        return spaceXCapsuleService.getAllCapsules().toString();
        return "Hello World";
    }

}
