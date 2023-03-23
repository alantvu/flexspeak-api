package dev.mjamieson.flexspeak.feature.integration.flat_icon;

import dev.mjamieson.flexspeak.feature.aac.AAC_Service;
import dev.mjamieson.flexspeak.feature.integration.flat_icon.service.external_api.FlatIconService;
import dev.mjamieson.flexspeak.feature.model.Sentence;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/flat_icon")
@RequiredArgsConstructor
public class FlatIconController {

    private final FlatIconService flatIconService;

    @GetMapping()
    public ResponseEntity<Void> getAllBy() {
        flatIconService.getAllBy();
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
