package dev.mjamieson.flexspeak.controller;

import dev.mjamieson.flexspeak.model.FirstClass;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    @GetMapping
    public ResponseEntity<FirstClass> index() {
        return new ResponseEntity<>(
                new FirstClass("LOLSTRING"),
                HttpStatus.OK
        );
    }
}
