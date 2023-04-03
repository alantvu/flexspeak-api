package dev.mjamieson.flexspeak.feature.aws_s3_bucket;

import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

public interface AmazonS3RequestFactory {
    GetObjectRequest createGetObjectRequest(String key);
    GetObjectPresignRequest createGetObjectPresignRequest(GetObjectRequest getObjectRequest);
}
