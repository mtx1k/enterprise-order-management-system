package com.final_project.ua_team_final_project.services;

import com.final_project.ua_team_final_project.config.DigitalOceanConfig;
import org.springframework.beans.factory.annotation.Value;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

public class StorageService {
    private final S3Client s3Client = DigitalOceanConfig.getS3Client();

    @Value("${do.bucket.name}")
    private String BUCKET_NAME;

    public List<String> listCsvFiles() {
        ListObjectsV2Request request = ListObjectsV2Request.builder()
                .bucket(BUCKET_NAME)
                .build();

        ListObjectsV2Response response = s3Client.listObjectsV2(request);

        return response.contents().stream()
                .map(S3Object::key)
                .filter(key -> key.endsWith(".csv"))
                .collect(Collectors.toList());
    }

    public InputStream downloadFile(String fileName) {
        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(BUCKET_NAME)
                .key(fileName)
                .build();
        return s3Client.getObject(request);
    }
}
