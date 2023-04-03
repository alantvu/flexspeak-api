package dev.mjamieson.flexspeak.feature.aws_s3_bucket;

import dev.mjamieson.flexspeak.config.AmazonS3Configuration;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
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
public class AmazonS3StorageServiceImpl implements AmazonS3StorageService, AmazonS3RequestFactory {

	private final AmazonS3Configuration amazonS3Configuration;
	private final S3Client s3Client;
	private final S3Presigner s3Presigner;
	@Override
	public void store(byte[] data, String fileName) throws IOException {
		PutObjectRequest objectRequest = PutObjectRequest.builder()
				.bucket(amazonS3Configuration.getS3bucketName())
				.key(fileName)
				.contentDisposition("inline")
				.build();
		s3Client.putObject(objectRequest, RequestBody.fromBytes(data));
	}

	@Override
	public void delete(String key) throws IOException {
		DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
				.bucket(amazonS3Configuration.getS3bucketName())
				.key(key)
				.build();
		s3Client.deleteObject(deleteObjectRequest);
	}

	@Override
	public String generatePresignedUrl(String key) {
		GetObjectRequest getObjectRequest = createGetObjectRequest(key);
		GetObjectPresignRequest getObjectPresignRequest = createGetObjectPresignRequest(getObjectRequest);
		PresignedGetObjectRequest presignedGetObjectRequest = s3Presigner.presignGetObject(getObjectPresignRequest);
		return presignedGetObjectRequest.url().toString();
	}
	@Override
	public List<String> generatePresignedUrls(List<String> keys) {
		return keys.stream()
				.map(this::generatePresignedUrl)
				.collect(Collectors.toList());
	}
	@Override
	public GetObjectRequest createGetObjectRequest(String key) {
		return GetObjectRequest.builder()
				.bucket(amazonS3Configuration.getS3bucketName())
				.key(key)
				.responseContentDisposition("inline")
				.build();
	}
	@Override
	public GetObjectPresignRequest createGetObjectPresignRequest(GetObjectRequest getObjectRequest) {
		return GetObjectPresignRequest.builder()
				.signatureDuration(Duration.ofSeconds(604800))
				.getObjectRequest(getObjectRequest)
				.build();
	}
}
