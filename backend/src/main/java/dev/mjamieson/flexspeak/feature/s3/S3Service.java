package dev.mjamieson.flexspeak.feature.s3;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class S3Service {
    private final S3Client s3;
    private final S3Presigner s3Presigner;

    public void putObject(String bucketName, String key, byte[] file) {
        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();
        s3.putObject(objectRequest, RequestBody.fromBytes(file));
    }

    public byte[] getObject(String bucketName, String key) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();
        ResponseInputStream<GetObjectResponse> res = s3.getObject(getObjectRequest);
        try {
            return res.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public String generatePresignedUrl(String bucketName, String key) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();
        GetObjectPresignRequest getObjectPresignRequest = createGetObjectPresignRequest(getObjectRequest);
        PresignedGetObjectRequest presignedGetObjectRequest = s3Presigner.presignGetObject(getObjectPresignRequest);
        return presignedGetObjectRequest.url().toString();
    }
    public List<String> generatePresignedUrls(String bucketName,List<String> keys) {
        return keys.stream()
                .map(key -> generatePresignedUrl(bucketName,key))
                .collect(Collectors.toList());
    }
    public GetObjectPresignRequest createGetObjectPresignRequest(GetObjectRequest getObjectRequest) {
        return GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofSeconds(604800))
                .getObjectRequest(getObjectRequest)
                .build();
    }

}
