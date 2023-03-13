package dev.mjamieson.flexspeak;

import dev.mjamieson.flexspeak.feature.user.auth.AuthenticationRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

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
    private static final String AUTHENTICATION_PATH = "/api/v1/auth";
    private static final String CUSTOMER_PATH = "/api/v1/customers";

    @Test
    void canLogin() {
        // Given
// create registration customerRegistrationRequest
//        Faker faker = new Faker();
//        Name fakerName = faker.name();
//
//        String name = fakerName.fullName();
        String email = "lsadjashjdh" + "-" + UUID.randomUUID() + "@amigoscode.com";
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
                .uri(AUTHENTICATION_PATH + "/authenticate")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(authenticationRequest), AuthenticationRequest.class)
                .exchange()
                .expectStatus()
                .isForbidden();
//
//        // send a post customerRegistrationRequest
//        webTestClient.post()
//                .uri(CUSTOMER_PATH)
//                .accept(MediaType.APPLICATION_JSON)
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(
//                        Mono.just(customerRegistrationRequest),
//                        CustomerRegistrationRequest.class
//                )
//                .exchange()
//                .expectStatus()
//                .isOk();
//
//        EntityExchangeResult<AuthenticationResponse> result = webTestClient.post()
//                .uri(AUTHENTICATION_PATH + "/login")
//                .accept(MediaType.APPLICATION_JSON)
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(Mono.just(authenticationRequest), AuthenticationRequest.class)
//                .exchange()
//                .expectStatus()
//                .isOk()
//                .expectBody(new ParameterizedTypeReference<AuthenticationResponse>() {
//                })
//                .returnResult();
//
//        String jwtToken = result.getResponseHeaders()
//                .get(HttpHeaders.AUTHORIZATION)
//                .get(0);
//
//        AuthenticationResponse authenticationResponse = result.getResponseBody();
//
//        CustomerDTO customerDTO = authenticationResponse.customerDTO();
//
//        assertThat(jwtUtil.isTokenValid(
//                jwtToken,
//                customerDTO.username())).isTrue();
//
//        assertThat(customerDTO.email()).isEqualTo(email);
//        assertThat(customerDTO.age()).isEqualTo(age);
//        assertThat(customerDTO.name()).isEqualTo(name);
//        assertThat(customerDTO.username()).isEqualTo(email);
//        assertThat(customerDTO.gender()).isEqualTo(gender);
//        assertThat(customerDTO.roles()).isEqualTo(List.of("ROLE_USER"));
//
    }
}
