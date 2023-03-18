package dev.mjamieson.flexspeak.feature.custom_aac;

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
@RequestMapping("/custom_aac")
@RequiredArgsConstructor
public class CustomAACController {
    private final CustomAACService flatIconService;

    @PostMapping()
    public ResponseEntity<Sentence> create(@AuthenticationPrincipal UserDetails userDetails, @RequestBody Sentence sentence) {
        return new ResponseEntity<>(flatIconService.post(userDetails.getUsername(), sentence),
                HttpStatus.CREATED
        );
    }
}
