package dev.mjamieson.flexspeak.bootstrap;

import dev.mjamieson.flexspeak.feature.open_ai.OpenAI_ImageRequest;
import dev.mjamieson.flexspeak.feature.open_ai.OpenAI_Service;
import dev.mjamieson.flexspeak.feature.s3.S3Buckets;
import dev.mjamieson.flexspeak.feature.s3.S3Service;
import dev.mjamieson.flexspeak.feature.user.auth.AuthenticationService;
import dev.mjamieson.flexspeak.feature.user.auth.RegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Dloader implements CommandLineRunner{
    private final AuthenticationService authenticationService;
    private final OpenAI_Service openAI_service;
    private final S3Service s3Service;
    private final S3Buckets s3Buckets;
    @Override
    public void run(String... args) throws Exception {
//        authData();
//        openAI_service.postImage(new OpenAI_ImageRequest("cat"));
//        testBucketUploadAndDownload();

    }

    private void testBucketUploadAndDownload() {
        s3Service.putObject(
                s3Buckets.getFlexspeak(),
                "foo",
                "Hello World".getBytes()
        );
        byte[] object = s3Service.getObject(
                s3Buckets.getFlexspeak(),
                "foo"
        );
        System.out.println("Hooray: " + new String(object));
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
