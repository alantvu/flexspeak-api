package dev.mjamieson.flexspeak.feature.s3;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class S3ServiceTest {

    @Mock
    private S3Client s3Client;

    @Mock
    private S3Presigner s3Presigner;

    @InjectMocks
    private S3Service underTest;

    @BeforeEach
    public void setUp() {
        s3Client = mock(S3Client.class);
        s3Presigner = mock(S3Presigner.class);
        underTest = new S3Service(s3Client, s3Presigner);

        // Mock AmazonS3Configuration values
//        lenient().when(amazonS3Configuration.getS3bucketName()).thenReturn("test-bucket");
//        lenient().when(amazonS3Configuration.getAccessKeyId()).thenReturn("test-access-key-id");
//        lenient().when(amazonS3Configuration.getSecretAccessKey()).thenReturn("test-secret-access-key");
    }

    @Test
    void putObject() {
        // Given
        String bucketName = "test-bucket";
        String key = "test-key";
        byte[] file = new byte[]{1, 2, 3};

        // When
        underTest.putObject(bucketName, key, file);

        // Then
        verify(s3Client, times(1)).putObject(any(PutObjectRequest.class), any(RequestBody.class));
    }

    @Test
    void getObject() throws IOException {
        // Given
//        String bucketName = "test-bucket";
//        String key = "test-key";
//        byte[] expectedBytes = new byte[]{1, 2, 3};
//
//        ResponseInputStream<GetObjectResponse> responseInputStream = mock(ResponseInputStream.class);
//        when(s3Client.getObject(any(GetObjectRequest.class))).thenReturn(responseInputStream);
//        when(responseInputStream.readAllBytes()).thenReturn(expectedBytes);
//
//        // When
//        byte[] result = underTest.getObject(bucketName, key);
//
//        // Then
//        assertArrayEquals(expectedBytes, result);
    }

    @SneakyThrows
    @Test
    void generatePresignedUrl() {
        // Given
        String bucketName = "test-bucket";
        String key = "test-key";

        GetObjectRequest getObjectRequest = GetObjectRequest.builder().bucket(bucketName).key(key).build();
        GetObjectPresignRequest getObjectPresignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofSeconds(604800))
                .getObjectRequest(getObjectRequest)
                .build();
        PresignedGetObjectRequest presignedGetObjectRequest = mock(PresignedGetObjectRequest.class);
        when(s3Presigner.presignGetObject(getObjectPresignRequest)).thenReturn(presignedGetObjectRequest);

        URI uri = URI.create("https://example.com/presignedUrl");
        URL url = uri.toURL();
        when(presignedGetObjectRequest.url()).thenReturn(url);

        // When
        String result = underTest.generatePresignedUrl(bucketName, key);

        // Then
        assertEquals("https://example.com/presignedUrl", result);
    }

}