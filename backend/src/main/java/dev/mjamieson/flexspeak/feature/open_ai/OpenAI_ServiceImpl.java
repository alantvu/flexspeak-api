package dev.mjamieson.flexspeak.feature.open_ai;

import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.image.CreateImageRequest;
import com.theokanning.openai.image.ImageResult;
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
import java.util.Arrays;
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
//        "You are an AAC system helping a non-speaking individual communicate efficiently by converting their message into a complete message."

        ChatMessage chatMessage = new ChatMessage("user",
                "You are an AAC system helping a non-speaking individual communicate efficiently by converting their message into a complete message. Your task is to generate accurate and meaningful messages based on the user's input."
                        + processedString);
        ChatCompletionRequest completionRequest = ChatCompletionRequest.builder()
                .messages(Arrays.asList(chatMessage))
                .model("gpt-3.5-turbo")
                .temperature(0.07)
                .topP(1.0)
                .build();
        List<ChatCompletionChoice> completionChoices = openAiService.createChatCompletion(completionRequest).getChoices();
        ChatCompletionChoice completionChoice = completionChoices.get(0);
        String aiSentence = completionChoice.getMessage().getContent();
        return Sentence.builder()
                .sentence(aiSentence)
                .build();
    }



    @Override
    public List<OpenAI_SuggestionsResponse> postSuggestion(String username, List<OpenAI_SuggestionRequest> openAI_suggestionRequests) {
        List<OpenAI_SuggestionsResponse> openAISuggestionsDTOS = new ArrayList<>();

        for (OpenAI_SuggestionRequest request : openAI_suggestionRequests) {
            ChatMessage chatMessage = new ChatMessage("user",
                    "Recommend 8 words to add to an augmentative and alternative communication (AAC) system of a user who enjoys discussing these topics, ONLY have the name of the food: USER INPUT: "
                            + request.request());
            OpenAI_SuggestionsResponse response = callOpen_AIGPT3_5(chatMessage, request);
            openAISuggestionsDTOS.add(response);
        }

        return openAISuggestionsDTOS;
    }

    @Override
    public OpenAI_ImageResponse postImage(OpenAI_ImageRequest openAI_imageRequest) {
        CreateImageRequest request =  CreateImageRequest.builder()
                .n(4)
                .prompt(openAI_imageRequest.searchText())
                .size("256x256")
                .responseFormat("b64_json")
                .build();
        ImageResult imageResult = openAiService.createImage(request);
//        List<String> b64JsonList = imageResult.getData().stream()
//                .map(Image::getB64Json)
//                .collect(Collectors.toList());

        return new OpenAI_ImageResponse(imageResult.getData());
    }

    @SneakyThrows
    private OpenAI_SuggestionsResponse callOpen_AIGPT3_5(ChatMessage chatMessage, OpenAI_SuggestionRequest openAI_suggestionRequest) {
        ChatCompletionRequest completionRequest = ChatCompletionRequest.builder()
                .messages(Arrays.asList(chatMessage))
                .model("gpt-3.5-turbo")
                .build();

        try {
            List<ChatCompletionChoice> completionChoices = openAiService.createChatCompletion(completionRequest).getChoices();
            ChatCompletionChoice completionChoice = completionChoices.get(0);
            String aiSentence = completionChoice.getMessage().getContent();
            List<String> aiSentenceArray = Arrays.asList(aiSentence.split("\\n"));
            List<String> cleanedSuggestions = aiSentenceArray.stream()
                    .map(suggestion -> suggestion.replaceAll("^\\d+\\.?\\s*", ""))
                    .collect(Collectors.toList());

            String subject = openAI_suggestionRequest.subject();

            return new OpenAI_SuggestionsResponse(subject, cleanedSuggestions);
        } catch (HttpException e) {
            System.out.println("An error occurred while calling OpenAI: " + e.getMessage());
            throw e;
        }
    }
}
