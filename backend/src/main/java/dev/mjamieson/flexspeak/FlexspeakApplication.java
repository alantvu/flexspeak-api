package dev.mjamieson.flexspeak;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

import java.time.Clock;

@SpringBootApplication
public class FlexspeakApplication {

	public static void main(String[] args) {
		String dbHost = System.getProperty("db.host");
		int dbPort = Integer.parseInt(System.getProperty("db.port"));
		String dbUser = System.getProperty("db.user");
		String dbPassword = System.getProperty("db.password");
		String dbName = System.getProperty("db.name");

		System.out.println("Database Host: " + dbHost);
		System.out.println("Database Port: " + dbPort);
		System.out.println("Database User: " + dbUser);
		System.out.println("Database Password: " + dbPassword);
		System.out.println("Database Name: " + dbName);

		new SpringApplicationBuilder(FlexspeakApplication.class)
				.build()
				.run(args);
	}

	@Bean
	Clock clock() {
		return Clock.systemUTC();
	}

}