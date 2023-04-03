package dev.mjamieson.flexspeak.feature.custom_word;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.mjamieson.flexspeak.feature.aws_s3_bucket.AmazonS3StorageService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
class CustomWordServiceImplTest {
    @Mock
    private CustomWordDAO customWordDAO;
    @Mock
    private AmazonS3StorageService amazonS3StorageService;
    @Mock
    private MultipartHttpServletRequest request;
    @Mock
    private MultipartFile multipartFile;

    private CustomWordService underTest;

    @BeforeEach
    void setUp() {
        underTest = new CustomWordServiceImpl(amazonS3StorageService, customWordDAO);
    }

    @SneakyThrows
    @Test
    void post_withImage() {
        // Given
        String username = "test@example.com";
        when(request.getParameter(any())).thenReturn("{}");
        when(request.getFile(any())).thenReturn(multipartFile);
        when(multipartFile.getBytes()).thenReturn(new byte[1]);
        when(multipartFile.getOriginalFilename()).thenReturn("test.jpg");

        // When
        underTest.post(username, request);

        // Then
        verify(customWordDAO, times(1)).save(eq(username), any(CustomWordDTO.class));
        verify(amazonS3StorageService, times(1)).store(any(), any());
    }

    @SneakyThrows
    @Test
    void post_withoutImage() {
        // Given
        String username = "test@example.com";
        when(request.getParameter(any())).thenReturn("{}");
        when(request.getFile(any())).thenReturn(null);

        // When
        underTest.post(username, request);

        // Then
        verify(customWordDAO, times(1)).save(eq(username), any(CustomWordDTO.class));
        verify(amazonS3StorageService, never()).store(any(), any());
    }

    @Test
    void get() {
        // Given
        String username = "test@example.com";
        CustomWord customWord = new CustomWord();
        customWord.setImagePath("test-image-path");
        List<CustomWord> customWords = List.of(customWord);
        when(customWordDAO.get(username)).thenReturn(customWords);
        when(amazonS3StorageService.generatePresignedUrl(any())).thenReturn("https://presigned-url.com");

        // When
        List<CustomWordDTO> result = underTest.get(username);

        // Then
        assertEquals(customWords.size(), result.size());
        verify(customWordDAO, times(1)).get(username);
        verify(amazonS3StorageService, times(1)).generatePresignedUrl(any());
    }
    @SneakyThrows
    @Test
    void post_withInvalidJson() {
        // Given
        String username = "test@example.com";
        when(request.getParameter(any())).thenReturn("{invalid-json}");
//        when(request.getFile(any())).thenReturn(null);

        // Then
        assertThrows(JsonProcessingException.class, () -> underTest.post(username, request));
        verify(customWordDAO, never()).save(any(), any());
        verify(amazonS3StorageService, never()).store(any(), any());
    }
    @SneakyThrows
    @Test
    void post_storageException() {
        // Given
        String username = "test@example.com";
        when(request.getParameter(any())).thenReturn("{}");
        when(request.getFile(any())).thenReturn(multipartFile);
        when(multipartFile.getBytes()).thenReturn(new byte[1]);
        when(multipartFile.getOriginalFilename()).thenReturn("test.jpg");
        doThrow(new RuntimeException("Storage error")).when(amazonS3StorageService).store(any(), any());

        // Then
        assertThrows(RuntimeException.class, () -> underTest.post(username, request));
        verify(customWordDAO, never()).save(any(), any());
    }

    @Test
    void get_imageWithoutUrl() {
        // Given
        String username = "test@example.com";
        CustomWord customWord = new CustomWord();
        customWord.setImagePath("test-image-path");
        List<CustomWord> customWords = List.of(customWord);
        when(customWordDAO.get(username)).thenReturn(customWords);
        when(amazonS3StorageService.generatePresignedUrl(any())).thenReturn(null);

        // When
        List<CustomWordDTO> result = underTest.get(username);

        // Then
        assertEquals(customWords.size(), result.size());
        assertNull(result.get(0).imagePath());
        verify(customWordDAO, times(1)).get(username);
        verify(amazonS3StorageService, times(1)).generatePresignedUrl(any());
    }


}