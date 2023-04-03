package dev.mjamieson.flexspeak.feature.user;

import dev.mjamieson.flexspeak.AbstractTestContainers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest extends AbstractTestContainers {

    @Autowired
    private UserRepository underTest;

    @BeforeEach
    void setUp() {
        underTest.deleteAll();
    }

    @Test
    void findByEmail() {
        // Given
        User testUser = createRandomUser();
        underTest.save(testUser);

        // When
        Optional<User> foundUser = underTest.findByEmail(testUser.getEmail());

        // Then
        assertTrue(foundUser.isPresent());
        assertEquals(testUser.getEmail(), foundUser.get().getEmail());
    }

    @Test
    void findByEmail_notFound() {
        // Given
        String nonExistentEmail = "nonexistent@example.com";

        // When
        Optional<User> foundUser = underTest.findByEmail(nonExistentEmail);

        // Then
        assertFalse(foundUser.isPresent());
    }
}
