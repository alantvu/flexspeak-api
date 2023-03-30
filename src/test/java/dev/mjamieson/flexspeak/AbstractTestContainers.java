package dev.mjamieson.flexspeak;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@TestPropertySource(properties = "spring.config.name:application,application-secret")
public abstract class AbstractTestContainers {

    @BeforeAll
    static void beforeAll() {
        Flyway flyway = Flyway.configure().dataSource(postgreSQLContainer.getJdbcUrl(), postgreSQLContainer.getUsername(), postgreSQLContainer.getPassword()).load();
        flyway.migrate();

    }

    @Container
    protected static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("dao-unit-test")
            .withUsername("admin")
            .withPassword("password");

    @DynamicPropertySource
    protected static void registerDataSourceProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url",postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.user",postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password",postgreSQLContainer::getPassword);
    }

}
