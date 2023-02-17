package dev.mjamieson.flexspeak.controller;

import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.engine.Engine;
import dev.mjamieson.flexspeak.integration.external.spacex.service.external_api.SpaceXCapsuleService;
import dev.mjamieson.flexspeak.model.FirstClass;
import dev.mjamieson.flexspeak.service.OpenAI_Service;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
    @GetMapping
    public ResponseEntity<FirstClass> index() {
        return new ResponseEntity<>(new FirstClass("LOLSTRING"),
                HttpStatus.OK
        );
    }
    @PostMapping()
    public ResponseEntity<FirstClass> create(@AuthenticationPrincipal UserDetails userDetails,@RequestBody FirstClass firstClass) {
        return new ResponseEntity<>(openAI_service.post(userDetails.getUsername(),firstClass),
                HttpStatus.CREATED
        );
    }


}
