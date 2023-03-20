package dev.mjamieson.flexspeak.feature.custom_word;

import dev.mjamieson.flexspeak.feature.model.Sentence;
import dev.mjamieson.flexspeak.feature.user.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/custom_word")
@RequiredArgsConstructor
public class CustomWordController {
    private final CustomWordService customWordService;
    @PostMapping()
    public ResponseEntity<Void> create(@AuthenticationPrincipal UserDetails userDetails, @RequestBody CustomWordRequest customWordRequest) {
        return new ResponseEntity<>(customWordService.post(userDetails.getUsername(), customWordRequest),
                HttpStatus.CREATED
        );
    }
    @GetMapping()
    public ResponseEntity<List<CustomWord>> get(@AuthenticationPrincipal UserDetails userDetails){
        List<CustomWord> customWords = customWordService.get(userDetails.getUsername());
        return ResponseEntity.ok(customWords);
    }
}
