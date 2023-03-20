package dev.mjamieson.flexspeak.feature.custom_word;

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
    public ResponseEntity<Void> create(@AuthenticationPrincipal UserDetails userDetails, @RequestBody CustomWordDTO customWordDTO) {
        return new ResponseEntity<>(customWordService.post(userDetails.getUsername(), customWordDTO),
                HttpStatus.CREATED
        );
    }
    @GetMapping()
    public ResponseEntity<List<CustomWordDTO>> get(@AuthenticationPrincipal UserDetails userDetails){
        List<CustomWordDTO> customWords = customWordService.get(userDetails.getUsername());
        return ResponseEntity.ok(customWords);
    }
}
