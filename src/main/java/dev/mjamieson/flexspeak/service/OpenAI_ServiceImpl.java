package dev.mjamieson.flexspeak.service;

import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.engine.Engine;
import dev.mjamieson.flexspeak.aac.AAC_Service;
import dev.mjamieson.flexspeak.annotation.CurrentUsername;
import dev.mjamieson.flexspeak.model.FirstClass;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OpenAI_ServiceImpl implements OpenAI_Service {

    @Value("${openAI.api-key}")
    private String apiKey;

    private OpenAiService openAiService;
    private final AAC_Service aac_service;
    @PostConstruct
    private void initOpenAI(){
        this.openAiService = new OpenAiService(apiKey);
    }

    @Override
    public FirstClass post(@CurrentUsername String username, FirstClass firstClass) {
        aac_service.postSentence(username,firstClass);
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
        List<CompletionChoice> completionChoices = openAiService.createCompletion("text-davinci-001",completionRequest).getChoices();
        System.out.println(completionChoices);
        String stringy = completionChoices.get(0).getText();
        return new FirstClass(stringy);
    }
}
