package dev.mjamieson.flexspeak.feature.open_ai;

import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;
import dev.mjamieson.flexspeak.feature.aac.AAC_Service;
import dev.mjamieson.flexspeak.annotation.CurrentUsername;
import dev.mjamieson.flexspeak.feature.model.Sentence;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public Sentence postSpeech(@CurrentUsername String username, Sentence sentence) {
        aac_service.postSentence(username,sentence);

        String processedString = sentence.sentence()
                .trim()
                .toLowerCase();

        // Remove all commas and full-stops
        processedString = processedString.replaceAll("[,.]", "");
//        processedString += " ->";
        List<String> stopList = new ArrayList<String>();
        stopList.add("\n");
        CompletionRequest completionRequest = CompletionRequest.builder()
                .prompt(processedString)
                .temperature(0.0)
                .frequencyPenalty(1.57)
                .maxTokens(20)
                .topP(1.0)
                .bestOf(1)
                .stop(stopList)
                .echo(false)
                .model("davinci")
                .build();
//        List<CompletionChoice> completionChoices = openAiService.createCompletion("ada:ft-personal:grammar-plus-2023-03-05-05-05-48",completionRequest).getChoices();
//        List<CompletionChoice> completionChoices = openAiService.createCompletion("gpt-3.5-turbo",completionRequest).getChoices();
        List<CompletionChoice> completionChoices = openAiService.createCompletion(completionRequest).getChoices();
        String aiSentence = completionChoices.get(0).getText();

        return Sentence.builder()
                .sentence(aiSentence)
                .build();
    }

    @Override
    public List<OpenAI_SuggestionsDTO> postSuggestion(String username, List<OpenAI_SuggestionsDTO>  openAI_suggestionsDTOS) {
        return null;
    }
}
