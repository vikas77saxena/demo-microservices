package com.example.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.service.S3FileStorageService;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
@ConditionalOnProperty(name = "aws.s3.enabled", havingValue = "true")
public class AwsS3Config {

    private static final Logger log = LoggerFactory.getLogger(AwsS3Config.class);

    private final String bucketName;
    private final String region;
    private final String accessKeyId;
    private final String secretAccessKey;

    public AwsS3Config(
            @Value("${aws.s3.bucket-name}") String bucketName,
            @Value("${aws.s3.region}") String region,
            @Value("${aws.access-key-id:${AWS_ACCESS_KEY_ID:}}") String accessKeyId,
            @Value("${aws.secret-access-key:${AWS_SECRET_ACCESS_KEY:}}") String secretAccessKey) {
        this.bucketName = bucketName;
        this.region = region;
        this.accessKeyId = accessKeyId.trim();
        this.secretAccessKey = secretAccessKey.trim();
        if (this.accessKeyId.isBlank() || this.secretAccessKey.isBlank()) {
            throw new IllegalStateException(
                    "AWS credentials missing. Create src/main/resources/application-local.yaml "
                            + "(copy from application-local.yaml.example). "
                            + "Put the secret in double quotes if it contains + or /.");
        }
        log.info("S3 enabled: bucket={}, region={}", bucketName, region);
    }

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKeyId, secretAccessKey)))
                .build();
    }

    @Bean
    public S3FileStorageService s3FileStorageService(S3Client s3Client) {
        return new S3FileStorageService(s3Client, bucketName);
    }
}
