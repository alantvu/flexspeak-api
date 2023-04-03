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
@TestPropertySource(properties = "spring.config.name:application,application-secret")
//@TestPropertySource(properties = {
//        "spring.config.name:application,application-secret",
//        "spring.datasource.hikari.maximum-pool-size:5",
//        "spring.datasource.hikari.idle-timeout:10000", // 10 seconds
//        "spring.datasource.hikari.min-idle:0",
//        "spring.datasource.hikari.connection-timeout:30000", // 30 seconds
//        "spring.datasource.hikari.max-lifetime:2000", // 30 seconds
//        "spring.datasource.hikari.testWhileIdle:true",
//        "spring.datasource.hikari.test-on-borrow:true",
//        "spring.datasource.hikari.leakDetectionThreshold:2000"
//})
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
