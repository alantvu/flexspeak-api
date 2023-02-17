package dev.mjamieson.flexspeak.feature.open_ai;

import dev.mjamieson.flexspeak.feature.model.Sentence;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/open_ai")
@RequiredArgsConstructor
public class OpenAIController {
    private final OpenAI_Service openAI_service;

    @PostMapping()
    public ResponseEntity<Sentence> create(@AuthenticationPrincipal UserDetails userDetails, @RequestBody Sentence sentence) {
        return new ResponseEntity<>(openAI_service.post(userDetails.getUsername(), sentence),
                HttpStatus.CREATED
        );
    }
}
