package dev.mjamieson.flexspeak.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "aws")
@Data
public class AmazonS3Configuration {

    private String s3bucketName;
    private String accessKeyId;
    private String secretAccessKey;

}
