package dev.mjamieson.flexspeak.feature.aws_s3_bucket;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
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
public class AmazonS3StorageServiceImpl implements AmazonS3StorageService {

	@Value("${aws.s3bucketName}")
	private String s3bucketName;
	@Value("${aws.accessKeyId}")
	private String accessKeyId;
	@Value("${aws.secretAccessKey}")
	private String secretAccessKey;
	private S3Client s3;

	@Override
	public void store(byte[] data, String fileName) throws IOException {
		AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKeyId, secretAccessKey);
		Region region = Region.US_EAST_1;
		s3 = S3Client.builder().region(region).credentialsProvider(StaticCredentialsProvider.create(awsCreds)).build();
		PutObjectRequest objectRequest = PutObjectRequest.builder().bucket(s3bucketName).key(fileName).build();
		s3.putObject(objectRequest, RequestBody.fromBytes(data));
	}

	@Override
	public void delete(String key) throws IOException {
		DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder().bucket(s3bucketName).key(key).build();
		s3.deleteObject(deleteObjectRequest);
	}

	@Override
	public String generatePresignedUrl(String key) {
		return generatePresignedUrls(List.of(key)).get(0);
	}

	public List<String> generatePresignedUrls(List<String> keys) {
		AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKeyId, secretAccessKey);
		Region region = Region.US_EAST_1;
		S3Presigner presigner = S3Presigner.builder().region(region).credentialsProvider(StaticCredentialsProvider.create(awsCreds)).build();

		return keys.stream()
				.map(key -> {
					GetObjectRequest getObjectRequest = GetObjectRequest.builder().bucket(s3bucketName).key(key).build();
					GetObjectPresignRequest getObjectPresignRequest = GetObjectPresignRequest.builder().signatureDuration(Duration.ofMinutes(60)).getObjectRequest(getObjectRequest).build();
					PresignedGetObjectRequest presignedGetObjectRequest = presigner.presignGetObject(getObjectPresignRequest);
					return presignedGetObjectRequest.url().toString();
				})
				.collect(Collectors.toList());
	}

	@PostConstruct
	private void initAPI() {
		AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKeyId, secretAccessKey);
		Region region = Region.US_EAST_1;
		s3 = S3Client.builder().region(region).credentialsProvider(StaticCredentialsProvider.create(awsCreds)).build();
	}
}
