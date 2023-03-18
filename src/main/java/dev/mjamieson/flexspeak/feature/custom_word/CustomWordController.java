package dev.mjamieson.flexspeak.feature.custom_word;

import dev.mjamieson.flexspeak.feature.model.Sentence;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/custom_word")
@RequiredArgsConstructor
public class CustomWordController {
    private final CustomWordService customWordService;

    @PostMapping()
    public ResponseEntity<Sentence> create(@AuthenticationPrincipal UserDetails userDetails, @RequestBody CustomWordRequest customWordRequest) {
        return new ResponseEntity<>(customWordService.post(userDetails.getUsername(), customWordRequest),
                HttpStatus.CREATED
        );
    }
}
