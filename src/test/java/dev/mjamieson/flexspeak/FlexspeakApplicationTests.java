package dev.mjamieson.flexspeak;

import dev.mjamieson.flexspeak.feature.model.Sentence;
import dev.mjamieson.flexspeak.feature.model.Word;
import dev.mjamieson.flexspeak.feature.open_ai.OpenAI_Service;
import dev.mjamieson.flexspeak.feature.user.auth.AuthenticationService;
import dev.mjamieson.flexspeak.feature.user.auth.RegisterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class FlexspeakApplicationTests {
	private final OpenAI_Service openAI_service;
	private final AuthenticationService authenticationService;
	@Autowired
	public FlexspeakApplicationTests(OpenAI_Service openAI_service, AuthenticationService authenticationService) {
		this.openAI_service = openAI_service;
		this.authenticationService = authenticationService;
	}

	@Test
	void contextLoads() {
		RegisterRequest registerRequest = RegisterRequest.builder()
				.firstname("mike")
				.lastname("jamieson")
				.email("michaeljamieson1@gmail.com")
				.password("password")
				.build();
//		authenticationService.register(registerRequest);
		List<Word> wordList = new ArrayList<>();
		wordList.addAll(Arrays.asList(new Word("I", LocalDateTime.now()), new Word("like", LocalDateTime.now())));


		Sentence sentence = Sentence.builder().words(wordList).sentence("I like eat chicken").build();
		openAI_service.post(registerRequest.getEmail(),sentence);

	}

}
