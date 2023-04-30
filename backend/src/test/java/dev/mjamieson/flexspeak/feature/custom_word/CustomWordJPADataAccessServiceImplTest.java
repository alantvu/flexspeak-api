package dev.mjamieson.flexspeak.feature.custom_word;

import dev.mjamieson.flexspeak.feature.user.User;
import dev.mjamieson.flexspeak.feature.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomWordJPADataAccessServiceImplTest {

    private CustomWordJPADataAccessServiceImpl underTest;
    @Mock
    private CustomWordRepository customWordRepository;
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        underTest = new CustomWordJPADataAccessServiceImpl(customWordRepository, userRepository);
    }

    @Test
    void save() {
        // Given
        User user = new User();
        String username = "test@example.com";
        CustomWordDTO customWordDTO = new CustomWordDTO("Hello", "Hello", "path/to/image", 1, 1, GridTitleEnum.ALL);

        when(userRepository.findByEmail(username)).thenReturn(Optional.of(user));
        when(customWordRepository.findByUserAndGridColumnAndGridRowAndGridTitleEnum(any(), any(), any(), any())).thenReturn(null);

        // When
        underTest.save(username, customWordDTO);

        // Then
        verify(customWordRepository, times(1)).save(any());
        verify(userRepository, never()).deleteAll();//keep for reference perhaps will go through and ensure everything is 100% tested like this
    }

    @Test
    void get() {
        // Given
        User user = new User();
        String username = "test@example.com";
        CustomWord customWord = new CustomWord();
        List<CustomWord> customWordList = Collections.singletonList(customWord);

        when(userRepository.findByEmail(username)).thenReturn(Optional.of(user));
        when(customWordRepository.findByUser(user)).thenReturn(customWordList);

        // When
        List<CustomWord> result = underTest.get(username);

        // Then
        assertEquals(customWordList, result);
        verify(customWordRepository, times(1)).findByUser(user);
    }
}