package com.example.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.service.S3FileStorageService;

import jakarta.annotation.PostConstruct;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
@ConditionalOnProperty(name = "aws.s3.enabled", havingValue = "true")
public class AwsS3Config {

    private static final Logger log = LoggerFactory.getLogger(AwsS3Config.class);

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    @Value("${aws.s3.region}")
    private String region;

    @Value("${aws.s3.excel.key:courses/uploads/courses.xlsx}")
    private String excelS3Key;

    @PostConstruct
    void logS3Config() {
        log.info("S3 enabled for Excel uploads: bucket={}, region={}, key={}", bucketName, region, excelS3Key);
    }

    @Bean
    public S3Client s3Client(@Value("${aws.s3.region}") String region) {
        return S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();
    }

    @Bean
    public S3FileStorageService s3FileStorageService(
            S3Client s3Client,
            @Value("${aws.s3.bucket-name}") String bucketName) {
        return new S3FileStorageService(s3Client, bucketName);
    }
}
