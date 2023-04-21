package dev.mjamieson.flexspeak.bootstrap;

import dev.mjamieson.flexspeak.feature.user.auth.AuthenticationService;
import dev.mjamieson.flexspeak.feature.user.auth.RegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Dloader implements CommandLineRunner{
    private final AuthenticationService authenticationService;
    @Override
    public void run(String... args) throws Exception {
//        authData();

    }

    private void authData() {
        RegisterRequest registerRequest = RegisterRequest.builder()
                .firstname("mike")
                .lastname("jamieson")
                .email("michaeljamieson@gmail.com")
                .password("password")
                .build();
        authenticationService.register(registerRequest);
    }

}
