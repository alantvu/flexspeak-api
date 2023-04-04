package dev.mjamieson.flexspeak.feature.aws_s3_bucket;

import dev.mjamieson.flexspeak.config.AmazonS3Configuration;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest.Builder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AmazonS3StorageServiceImplTest {

    @Mock
    private S3Client s3Client;

    @Mock
    private S3Presigner s3Presigner;

    @Mock
    private AmazonS3Configuration amazonS3Configuration;

    @InjectMocks
    private AmazonS3StorageServiceImpl underTest;

    @BeforeEach
    public void setUp() {
        amazonS3Configuration = mock(AmazonS3Configuration.class);
        s3Client = mock(S3Client.class);
        s3Presigner = mock(S3Presigner.class);
        underTest = new AmazonS3StorageServiceImpl(amazonS3Configuration, s3Client, s3Presigner);

        // Mock AmazonS3Configuration values
        lenient().when(amazonS3Configuration.getS3bucketName()).thenReturn("test-bucket");
        lenient().when(amazonS3Configuration.getAccessKeyId()).thenReturn("test-access-key-id");
        lenient().when(amazonS3Configuration.getSecretAccessKey()).thenReturn("test-secret-access-key");
    }

    @Test
    void store() throws IOException {
        // Given
        byte[] data = new byte[]{1, 2, 3};
        String fileName = "test-file.png";

        // When
        underTest.store(data, fileName);

        // Then
        verify(s3Client, times(1)).putObject(any(PutObjectRequest.class), any(RequestBody.class));
    }

    @Test
    void delete() throws IOException {
        // Given
        String key = "test-file.txt";

        // When
        underTest.delete(key);

        // Then
        verify(s3Client, times(1)).deleteObject(any(DeleteObjectRequest.class));
    }



    @SneakyThrows
    @Test
    void generatePresignedUrl() {
        // Given
        String key = "test-file.txt";
        GetObjectRequest getObjectRequest = underTest.createGetObjectRequest(key);
        GetObjectPresignRequest getObjectPresignRequest = underTest.createGetObjectPresignRequest(getObjectRequest);
        PresignedGetObjectRequest presignedGetObjectRequest = mock(PresignedGetObjectRequest.class);
//
        when(s3Presigner.presignGetObject(getObjectPresignRequest)).thenReturn(presignedGetObjectRequest);
        URI uri = URI.create("https://example.com/presignedUrl");
        URL url = uri.toURL();
        when(presignedGetObjectRequest.url()).thenReturn(url);

        // When
        String result = underTest.generatePresignedUrl(key);

        // Then
        assertEquals("https://example.com/presignedUrl", result);
    }

    @SneakyThrows
    @Test
    void generatePresignedUrls() {
        // Given
        List<String> keys = List.of("file1.txt", "file2.txt");

        // Mock presignedGetObjectRequest
        PresignedGetObjectRequest presignedGetObjectRequest1 = mock(PresignedGetObjectRequest.class);
        PresignedGetObjectRequest presignedGetObjectRequest2 = mock(PresignedGetObjectRequest.class);

        // Mock presigned URLs
        URI uri1 = URI.create("https://example.com/presignedUrl1");
        URL url1 = uri1.toURL();
        URI uri2 = URI.create("https://example.com/presignedUrl2");
        URL url2 = uri2.toURL();

        when(s3Presigner.presignGetObject(any(GetObjectPresignRequest.class)))
                .thenReturn(presignedGetObjectRequest1, presignedGetObjectRequest2);
        when(presignedGetObjectRequest1.url()).thenReturn(url1);
        when(presignedGetObjectRequest2.url()).thenReturn(url2);

        // When
        List<String> result = underTest.generatePresignedUrls(keys);

        // Then
        List<String> expectedUrls = List.of("https://example.com/presignedUrl1", "https://example.com/presignedUrl2");
        assertEquals(expectedUrls, result);
    }

}