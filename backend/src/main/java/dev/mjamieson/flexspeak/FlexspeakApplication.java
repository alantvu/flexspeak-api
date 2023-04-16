package dev.mjamieson.flexspeak;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

import java.time.Clock;

@SpringBootApplication
public class FlexspeakApplication {
	public static void main(String[] args) {
		new SpringApplicationBuilder(FlexspeakApplication.class)
				.build()
				.run(args);
	}




	@Bean
	Clock clock() {
		return Clock.systemUTC();
	}


}