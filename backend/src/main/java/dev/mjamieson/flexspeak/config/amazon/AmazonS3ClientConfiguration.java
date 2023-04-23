package dev.mjamieson.flexspeak.config.amazon;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
@RequiredArgsConstructor
public class AmazonS3ClientConfiguration {
    private final AmazonS3Configuration amazonS3Configuration;

//    @Bean
//    public S3Client s3Client() {
//        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(amazonS3Configuration.getAccessKeyId(), amazonS3Configuration.getSecretAccessKey());
//        Region region = Region.US_EAST_1; // You can set the region from the configuration
//        StaticCredentialsProvider staticCredentialsProvider = StaticCredentialsProvider.create(awsCreds);
//        return S3Client.builder().region(region).credentialsProvider(staticCredentialsProvider).build();
//    }

//    @Bean
//    public S3Presigner s3Presigner() {
//        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(amazonS3Configuration.getAccessKeyId(), amazonS3Configuration.getSecretAccessKey());
//        Region region = Region.US_EAST_1; // You can set the region from the configuration
//        StaticCredentialsProvider staticCredentialsProvider = StaticCredentialsProvider.create(awsCreds);
//        return S3Presigner.builder().region(region).credentialsProvider(staticCredentialsProvider).build();
//    }

}
