package dev.mjamieson.flexspeak.feature.custom_word;

import dev.mjamieson.flexspeak.AbstractTestContainers;
import dev.mjamieson.flexspeak.feature.user.User;
import dev.mjamieson.flexspeak.feature.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomWordRepositoryTest extends AbstractTestContainers {

    @Autowired
    private CustomWordRepository underTest;

    @Autowired
    private UserRepository userRepository;


    private User testUser;

    @BeforeEach
    void setUp() {
        underTest.deleteAll();
        userRepository.deleteAll();
        testUser = userRepository.save(createRandomUser());
    }

    private CustomWord createAndSaveRandomCustomWord(User user) {
        CustomWordDTO customWordDTO = new CustomWordDTO(
                FAKER.lorem().word(),
                FAKER.lorem().word(),
                FAKER.internet().image(),
                FAKER.number().numberBetween(1, 10),
                FAKER.number().numberBetween(1, 10),
                GridTitleEnum.ALL
        );

        CustomWord customWord = new CustomWord(
                null,
                customWordDTO.wordToDisplay(),
                customWordDTO.wordToSpeak(),
                customWordDTO.imagePath(),
                customWordDTO.gridRow(),
                customWordDTO.gridColumn(),
                customWordDTO.gridTitleEnum(),
                null,
                null,
                user
        );

        return underTest.save(customWord);
    }

    @Test
    void deleteByUser() {
        // Given
        CustomWord customWord = createAndSaveRandomCustomWord(testUser);

        // When
        underTest.deleteByUser(testUser);

        // Then
        assertFalse(underTest.existsById(customWord.getId()));
    }

    @Test
    void findByUser() {
        // Given
        CustomWord customWord = createAndSaveRandomCustomWord(testUser);

        // When
        var customWords = underTest.findByUser(testUser);

        // Then
        assertEquals(1, customWords.size());
        assertEquals(customWord.getId(), customWords.get(0).getId());
    }

    @Test
    void findByUserAndGridColumnAndGridRowAndGridTitleEnum() {
        // Given
        CustomWord customWord = createAndSaveRandomCustomWord(testUser);

        // When
        var foundCustomWord = underTest.findByUserAndGridColumnAndGridRowAndGridTitleEnum(testUser, customWord.getGridColumn(), customWord.getGridRow(), customWord.getGridTitleEnum());

        // Then
        assertNotNull(foundCustomWord);
        assertEquals(customWord.getId(), foundCustomWord.getId());
    }
    @Test
    void findByUser_noRecords() {
        // When
        var customWords = underTest.findByUser(testUser);

        // Then
        assertTrue(customWords.isEmpty());
    }

    @Test
    void findByUserAndGridColumnAndGridRowAndGridTitleEnum_noMatchingRecord() {
        // Given
        createAndSaveRandomCustomWord(testUser);

        // When
        var nonExistentGridColumn = FAKER.number().numberBetween(11, 20);
        var nonExistentGridRow = FAKER.number().numberBetween(11, 20);
        var foundCustomWord = underTest.findByUserAndGridColumnAndGridRowAndGridTitleEnum(testUser, nonExistentGridColumn, nonExistentGridRow, GridTitleEnum.ALL);

        // Then
        assertNull(foundCustomWord);
    }

}
