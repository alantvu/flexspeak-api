package dev.mjamieson.flexspeak.feature.aac;


import dev.mjamieson.flexspeak.feature.model.Sentence;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/aac")
@RequiredArgsConstructor
public class AAC_Controller {

    private final AAC_Service aac_service;

    @PostMapping()
    public ResponseEntity<Void> create(@RequestBody Sentence sentence,
                                       @AuthenticationPrincipal UserDetails userDetails
    ) {
        return new ResponseEntity<>(aac_service.postSentence(userDetails.getUsername(), sentence),
                HttpStatus.CREATED
        );
    }

}
