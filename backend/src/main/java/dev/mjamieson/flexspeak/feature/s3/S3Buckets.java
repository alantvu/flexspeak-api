package dev.mjamieson.flexspeak.feature.s3;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "aws.s3.buckets")
public class S3Buckets {
    private String flexspeak;

    public String getFlexspeak() {
        return flexspeak;
    }

    public void setFlexspeak(String flexspeak) {
        this.flexspeak = flexspeak;
    }
}
