package dev.mjamieson.flexspeak.feature.aac;

import dev.mjamieson.flexspeak.AbstractTestContainers;
import dev.mjamieson.flexspeak.feature.user.User;
import dev.mjamieson.flexspeak.feature.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AAC_RepositoryTest extends AbstractTestContainers {

    @Autowired
    private AAC_Repository underTest;

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = userRepository.save(createRandomUser());
    }

    @Test
    void countByUser() {
        // Given
        AAC aac1 = AAC.builder().user(user).sentence("Hello, world!").build();
        AAC aac2 = AAC.builder().user(user).sentence("How are you?").build();
        underTest.save(aac1);
        underTest.save(aac2);

        // When
        long count = underTest.countByUser(user);

        // Then
        assertEquals(2, count);
    }

    @Test
    void deleteByUser() {
        // Given
        AAC aac1 = AAC.builder().user(user).sentence("Hello, world!").build();
        AAC aac2 = AAC.builder().user(user).sentence("How are you?").build();
        underTest.save(aac1);
        underTest.save(aac2);

        // When
        underTest.deleteByUser(user);

        // Then
        long count = underTest.countByUser(user);
        assertEquals(0, count);
    }
}
