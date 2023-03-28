package dev.mjamieson.flexspeak.feature.aws_s3_bucket;

import java.io.IOException;

public interface AmazonS3StorageService {

    void store(byte[] data, String fileName) throws IOException;

    void delete(String key) throws IOException;
    
    String generatePresignedUrl(String key);
    

}
