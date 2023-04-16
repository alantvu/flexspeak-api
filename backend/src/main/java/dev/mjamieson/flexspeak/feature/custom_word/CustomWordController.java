package dev.mjamieson.flexspeak.feature.custom_word;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;


@RestController
@RequestMapping("/custom_word")
@RequiredArgsConstructor
public class CustomWordController {
    private final CustomWordService customWordService;
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> create(@AuthenticationPrincipal UserDetails userDetails,
                                       MultipartHttpServletRequest request) {
        return new ResponseEntity<>(customWordService.post(userDetails.getUsername(), request),
                HttpStatus.CREATED
        );
    }
    @PostMapping("/bulk-create")
    public ResponseEntity<Void> createBulk(
            @AuthenticationPrincipal UserDetails userDetails,
            MultipartHttpServletRequest request
    ) {
        customWordService.posts(userDetails.getUsername(), request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping()
    public ResponseEntity<List<CustomWordDTO>> get(@AuthenticationPrincipal UserDetails userDetails){
        List<CustomWordDTO> customWords = customWordService.get(userDetails.getUsername());
        return ResponseEntity.ok(customWords);
    }
}
