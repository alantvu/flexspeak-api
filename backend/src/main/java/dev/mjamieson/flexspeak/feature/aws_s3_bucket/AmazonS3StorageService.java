package dev.mjamieson.flexspeak.feature.aws_s3_bucket;

import java.io.IOException;
import java.util.List;

public interface AmazonS3StorageService {

    void store(byte[] data, String fileName) throws IOException;

    void delete(String key) throws IOException;
    
    String generatePresignedUrl(String key);
    List<String> generatePresignedUrls(List<String> keys);


}
