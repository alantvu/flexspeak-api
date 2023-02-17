package dev.mjamieson.flexspeak.controller;

import dev.mjamieson.flexspeak.model.SentenceRequest;
import dev.mjamieson.flexspeak.model.SentenceResponse;
import dev.mjamieson.flexspeak.service.OpenAI_Service;
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
    public ResponseEntity<SentenceResponse> create(@AuthenticationPrincipal UserDetails userDetails, @RequestBody SentenceRequest sentenceRequest) {
        return new ResponseEntity<>(openAI_service.post(userDetails.getUsername(), sentenceRequest),
                HttpStatus.CREATED
        );
    }
}
