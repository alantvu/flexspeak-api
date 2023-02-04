package dev.mjamieson.flexspeak.controller;

import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;
import dev.mjamieson.flexspeak.integration.external.spacex.service.external_api.SpaceXCapsuleService;
import dev.mjamieson.flexspeak.service.OpenAIService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/open_ai")
@RequiredArgsConstructor
public class OpenAiController {
    private final OpenAIService openAIService;
    private final SpaceXCapsuleService spaceXCapsuleService;

    @Value("${openAI.api-key}")
    private String apiKey;

    @GetMapping
    public String index() {
//        return spaceXCapsuleService.getAllCapsules().toString();
        OpenAiService service = new OpenAiService(apiKey);
        CompletionRequest completionRequest = CompletionRequest.builder()
                .prompt("can you tell me about main coone cats")
                .echo(false)
                .build();
        List<CompletionChoice> completionChoices = service.createCompletion("ada",completionRequest).getChoices();
        return "Hello World";
    }

}
