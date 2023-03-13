package dev.mjamieson.flexspeak;

import dev.mjamieson.flexspeak.feature.model.Sentence;
import dev.mjamieson.flexspeak.feature.model.Word;
import dev.mjamieson.flexspeak.feature.open_ai.OpenAI_Service;
import dev.mjamieson.flexspeak.feature.user.auth.AuthenticationRequest;
import dev.mjamieson.flexspeak.feature.user.auth.AuthenticationResponse;
import dev.mjamieson.flexspeak.feature.user.auth.RegisterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;


@SpringBootTest(webEnvironment = RANDOM_PORT)
public class FlexspeakApplicationTests extends AbstractTestContainers {


    @Autowired
    private WebTestClient webTestClient;

//    @Autowired
//    private JWTUtil jwtUtil;

    //    private static final Random RANDOM = new Random();
    private static final String AUTHENTICATION_PATH = "/auth/authenticate";
    private static final String REGISTER_PATH = "/auth/register";
    private static final String CUSTOMER_PATH = "/api/v1/customers";
    private static final String OPEN_AI_PATH = "/open_ai";

    @Test
    void canLogin() {
        // Given
// create registration customerRegistrationRequest
//        Faker faker = new Faker();
//        Name fakerName = faker.name();
//
//        String name = fakerName.fullName();
//        String email = "lsadjashjdh" + "-" + UUID.randomUUID() + "@amigoscode.com";
        String email = "michaeljamieson@gmail.com";
//        int age = RANDOM.nextInt(1, 100);
//
//        Gender gender = age % 2 == 0 ? Gender.MALE : Gender.FEMALE;
//
        String password = "password";
//
//        CustomerRegistrationRequest customerRegistrationRequest = new CustomerRegistrationRequest(
//                name, email, password, age, gender
//        );
//
        AuthenticationRequest authenticationRequest = new AuthenticationRequest(
                email,
                password
        );
//
        webTestClient.post()
                .uri(AUTHENTICATION_PATH )
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(authenticationRequest), AuthenticationRequest.class)
                .exchange()
                .expectStatus()
                .isForbidden();

        RegisterRequest registerRequest = RegisterRequest.builder()
                .firstname("michael")
                .lastname("jamieson")
                .email("michaeljamieson@gmail.com")
                .password("password")
                .build();
        webTestClient.post()
                .uri(REGISTER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        Mono.just(registerRequest),
                        RegisterRequest.class
                )
                .exchange()
                .expectStatus()
                .isOk();

        EntityExchangeResult<AuthenticationResponse> result = webTestClient.post()
                .uri(AUTHENTICATION_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(authenticationRequest), AuthenticationRequest.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<AuthenticationResponse>() {
                })
                .returnResult();
        String token = result.getResponseBody().getToken();

        List<Word> words = new ArrayList<>();
        words.add(new Word("hello", LocalDateTime.now()));
        words.add(new Word("world", LocalDateTime.now()));
        words.add(new Word("example", LocalDateTime.now()));
        String sentencer = String.join(" ", words.stream().map(Word::word).toArray(String[]::new));
        Sentence sentence = Sentence.builder()
                .words(words)
                .sentence(sentencer)
                .build();

        webTestClient.post()
                .uri(OPEN_AI_PATH)
                .header("Authorization", "Bearer " + token) // Add the token to the Bearer header
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        Mono.just(sentence),
                        Sentence.class
                )
                .exchange()
                .expectStatus()
                .isOk();

    }
}
