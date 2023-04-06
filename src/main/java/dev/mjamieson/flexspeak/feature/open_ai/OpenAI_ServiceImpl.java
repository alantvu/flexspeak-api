package dev.mjamieson.flexspeak.feature.open_ai;

import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import dev.mjamieson.flexspeak.feature.aac.AAC_Service;
import dev.mjamieson.flexspeak.annotation.CurrentUsername;
import dev.mjamieson.flexspeak.feature.model.Sentence;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.HttpException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OpenAI_ServiceImpl implements OpenAI_Service {

    @Value("${openAI.api-key}")
    private String apiKey;

    private OpenAiService openAiService;
    private final AAC_Service aac_service;

    @PostConstruct
    private void initOpenAI() {
        this.openAiService = new OpenAiService(apiKey);
    }

    @Override
    public Sentence postSpeech(@CurrentUsername String username, Sentence sentence) {
        aac_service.postSentence(username, sentence);

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
    public List<OpenAI_SuggestionsDTO> postSuggestion(String username, List<OpenAI_SuggestionsDTO> openAI_suggestionsDTOS) {

        List<OpenAI_SuggestionsDTO> openAISuggestionsDTOS = openAI_suggestionsDTOS.stream()
                .map(this::callOpen_AI)
                .collect(Collectors.toList());
        return openAISuggestionsDTOS;
    }

    @SneakyThrows
    private OpenAI_SuggestionsDTO callOpen_AI(OpenAI_SuggestionsDTO openAI_suggestionsDTO) {
        List<String> stopList = new ArrayList<String>();
        stopList.add("\n");

        List<ChatMessage> chatMessages = new ArrayList<>();
        chatMessages.add(new ChatMessage("user", "Recommend 8 words to add to an augmentative and alternative communication (AAC) system of a user who enjoys discussing these topics: USER INPUT: " + openAI_suggestionsDTO.openAI_Suggestions()));

        ChatCompletionRequest completionRequest = ChatCompletionRequest.builder()
                .messages(chatMessages)
//                .prompt(
//                        "Recommend 8 words to add to an augmentative and alternative communication (AAC) system of a user who enjoys discussing these topics:" +
//                               " USER INPUT: " + openAI_suggestionsDTO.openAI_Suggestions())
//                .echo(false)
//                .model("davinci")
                .model("gpt-3.5-turbo")
//                .model("gpt-4")
                .build();

        try {
            List<ChatCompletionChoice> completionChoices = openAiService.createChatCompletion(completionRequest).getChoices();
            String aiSentence = completionChoices.get(0).toString();
            return new OpenAI_SuggestionsDTO(
                    openAI_suggestionsDTO.subject(),
                    aiSentence
            );
        } catch (HttpException e) {
            System.out.println("An error occurred while calling OpenAI: " + e.getMessage());
            throw e;

        }
    }
}
