package dev.mjamieson.flexspeak;

import dev.mjamieson.flexspeak.integration.external.spacex.service.external_api.SpaceXCapsuleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FlexspeakApplicationTests {

	private final SpaceXCapsuleService spaceXCapsuleService;

	@Autowired
	FlexspeakApplicationTests(SpaceXCapsuleService spaceXCapsuleService) {
		this.spaceXCapsuleService = spaceXCapsuleService;
	}

	@Test
	void contextLoads() {
		spaceXCapsuleService.getAllCapsules();

	}

}
