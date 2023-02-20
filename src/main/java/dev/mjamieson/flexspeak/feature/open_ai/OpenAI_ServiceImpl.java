package dev.mjamieson.flexspeak.feature.open_ai;

import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.engine.Engine;
import dev.mjamieson.flexspeak.feature.aac.AAC_Service;
import dev.mjamieson.flexspeak.annotation.CurrentUsername;
import dev.mjamieson.flexspeak.feature.model.Sentence;
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
    public Sentence post(@CurrentUsername String username, Sentence sentence) {
        aac_service.postSentence(username,sentence);
        // Convert the string to lowercase
        String processedString = sentence.sentence().toLowerCase();

        // Remove all commas and full-stops
        processedString = processedString.replaceAll("[,.]", "");
        processedString += " ->";

        CompletionRequest completionRequest = CompletionRequest.builder()
                .prompt(processedString)
                .temperature(0.4)
                .frequencyPenalty(1.57)
                .topP(1.0)
                .bestOf(1)
                .echo(false)
                .build();
        List<CompletionChoice> completionChoices = openAiService.createCompletion("ada:ft-personal-2023-02-19-03-57-02",completionRequest).getChoices();
        String aiSentence = completionChoices.get(0).getText();
        String[] parts = aiSentence.split("\n\n");
        return Sentence.builder()
                .sentence(parts[0])
//                .sentence(sentence.sentence())
                .build();
    }
}
