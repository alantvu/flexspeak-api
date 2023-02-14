package dev.mjamieson.flexspeak.controller;

import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.engine.Engine;
import dev.mjamieson.flexspeak.integration.external.spacex.service.external_api.SpaceXCapsuleService;
import dev.mjamieson.flexspeak.model.FirstClass;
import dev.mjamieson.flexspeak.service.OpenAIService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/open_ai")
@RequiredArgsConstructor
public class OpenAIController {
    private final OpenAIService openAIService;
    private final SpaceXCapsuleService spaceXCapsuleService;
    private  OpenAiService openAiService;

    @Value("${openAI.api-key}")
    private String apiKey;

    @GetMapping
    public ResponseEntity<FirstClass> index() {
//        return spaceXCapsuleService.getAllCapsules().toString();
//        OpenAiService service = new OpenAiService(apiKey);
//        CompletionRequest completionRequest = CompletionRequest.builder()
//                .prompt("can you tell me about main coone cats")
//                .echo(false)
//                .build();
//        List<CompletionChoice> completionChoices = service.createCompletion("ada",completionRequest).getChoices();
        return new ResponseEntity<>(new FirstClass("LOLSTRING"),
                HttpStatus.OK
        );
    }

    @PostMapping()
    public ResponseEntity<FirstClass> create(@RequestBody FirstClass firstClass) {
        CompletionRequest completionRequest = CompletionRequest.builder()
                .prompt(
                        "Convert this aac output to natural language output," +
                                "Want Outside ?" +
                                "Natural language output for this is , " + "“I want to go outside”" + firstClass.stringy() +
                                "Convert this aac output to natural language output," +
                                firstClass.stringy() +
                                "Natural language output for this is "

                )
                .temperature(0.4)
                .frequencyPenalty(1.57)
                .topP(1.0)
                .bestOf(1)
                .echo(false)
                .build();
        List<Engine> engines = openAiService.getEngines();
//        List<CompletionChoice> completionChoices = openAiService.createCompletion("ada",completionRequest).getChoices();
//        List<CompletionChoice> completionChoices = openAiService.createCompletion("text-davinci-001",completionRequest).getChoices();
//        System.out.println(completionChoices);
//        String stringy = completionChoices.get(0).getText();
//        return new ResponseEntity<>(new FirstClass(stringy),
        return new ResponseEntity<>(new FirstClass("stringy"),
                HttpStatus.CREATED
        );
    }

    @PostConstruct
    private void initOpenAI(){
        this.openAiService = new OpenAiService(apiKey);
    }

}
