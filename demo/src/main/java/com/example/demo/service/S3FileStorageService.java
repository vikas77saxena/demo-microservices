package com.example.demo.service;

import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

public class S3FileStorageService {

    private static final Logger logger = LoggerFactory.getLogger(S3FileStorageService.class);

    private final S3Client s3Client;
    private final String bucketName;

    public S3FileStorageService(S3Client s3Client, String bucketName) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
    }

    public String uploadFile(String localFilePath, String s3Key) {
        Path path = Path.of(localFilePath);
        if (!Files.exists(path)) {
            throw new IllegalArgumentException("Local file not found for S3 upload: " + localFilePath);
        }

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(s3Key)
                .build();

        s3Client.putObject(request, RequestBody.fromFile(path));
        String location = "s3://" + bucketName + "/" + s3Key;
        logger.info("Uploaded {} to {}", localFilePath, location);
        return location;
    }
}
