package dev.mjamieson.flexspeak.feature.open_ai;

import dev.mjamieson.flexspeak.feature.model.Sentence;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/open_ai")
@RequiredArgsConstructor
public class OpenAIController {
    private final OpenAI_Service openAI_service;

    @PostMapping("/speech")
    public ResponseEntity<Sentence> createSpeech(@AuthenticationPrincipal UserDetails userDetails, @RequestBody Sentence sentence) {
        Sentence ai_sentence = openAI_service.postSpeech(userDetails.getUsername(), sentence);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ai_sentence);
    }

    @PostMapping("/suggestion")
    public ResponseEntity<List<OpenAI_SuggestionsDTO>> createSuggestions(@AuthenticationPrincipal UserDetails userDetails, @RequestBody List<OpenAI_SuggestionsDTO> openAI_suggestionsDTOS) {
        List<OpenAI_SuggestionsDTO> createdSuggestions = openAI_service.postSuggestion(userDetails.getUsername(), openAI_suggestionsDTOS);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createdSuggestions);
    }
}
