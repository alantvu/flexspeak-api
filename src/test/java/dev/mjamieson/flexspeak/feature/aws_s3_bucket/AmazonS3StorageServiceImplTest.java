package dev.mjamieson.flexspeak.feature.aws_s3_bucket;

import dev.mjamieson.flexspeak.config.AmazonS3Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
import java.time.Duration;
import java.util.List;

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
    void setUp() throws URISyntaxException {
        // Set up the mock values for AmazonS3Configuration
        when(amazonS3Configuration.getS3bucketName()).thenReturn("YOUR_BUCKET_NAME");

        // Set up mock behavior for s3Presigner
//        PresignedGetObjectRequest presignedGetObjectRequest = PresignedGetObjectRequest.builder()
//                .getObjectRequest(GetObjectRequest.builder().build())
//                .signatureDuration(Duration.ofSeconds(604800))
//                .url(new URI("https://example.com/presignedUrl"))
//                .build();
//
//        when(s3Presigner.presignGetObject(any(GetObjectPresignRequest.class))).thenReturn(presignedGetObjectRequest);
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

    @Test
    @Disabled
    void generatePresignedUrl() {
        // Given
        String key = "test-file.txt";

        // When
        underTest.generatePresignedUrl(key);

        // Then
        verify(s3Presigner, times(1)).presignGetObject(any(GetObjectPresignRequest.class));
    }

    @Test
    @Disabled
    void generatePresignedUrls() {
        // Given
        List<String> keys = List.of("file1.txt", "file2.txt");

        // When
        underTest.generatePresignedUrls(keys);

        // Then
        verify(s3Presigner, times(2)).presignGetObject(any(GetObjectPresignRequest.class));
    }
}
