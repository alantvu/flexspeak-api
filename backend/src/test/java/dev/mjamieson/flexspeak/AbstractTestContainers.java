package dev.mjamieson.flexspeak;

import dev.mjamieson.flexspeak.feature.user.Role;
import dev.mjamieson.flexspeak.feature.user.User;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import com.github.javafaker.Faker;
import org.springframework.boot.jdbc.DataSourceBuilder;
import javax.sql.DataSource;
import java.sql.SQLException;

@Testcontainers
public abstract class AbstractTestContainers {

    @BeforeAll
    static void beforeAll() {
        Flyway flyway = Flyway.configure().dataSource(postgreSQLContainer.getJdbcUrl(), postgreSQLContainer.getUsername(), postgreSQLContainer.getPassword()).load();
        flyway.migrate();

    }
//    @AfterEach
//    void afterEach() throws SQLException {
//        getDataSource().getConnection().close();
//    }


    @Container
    protected static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15.2")
            .withDatabaseName("customer")
            .withUsername("flexspeak123")
            .withPassword("password123");

    @DynamicPropertySource
    protected static void registerDataSourceProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url",postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.user",postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password",postgreSQLContainer::getPassword);
    }

    protected static final Faker FAKER = new Faker();

    protected User createRandomUser() {
        return User.builder()
                .firstname(FAKER.name().firstName())
                .lastname(FAKER.name().lastName())
                .email(FAKER.internet().emailAddress())
                .password(FAKER.internet().password())
                .role(Role.USER) // Or any other role defined in your Role enum
                .build();
    }
    private static DataSource getDataSource() {
        return DataSourceBuilder.create()
                .driverClassName(postgreSQLContainer.getDriverClassName())
                .url(postgreSQLContainer.getJdbcUrl())
                .username(postgreSQLContainer.getUsername())
                .password(postgreSQLContainer.getPassword())
                .build();
    }

}
