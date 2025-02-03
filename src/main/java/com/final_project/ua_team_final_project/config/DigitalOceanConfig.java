package com.final_project.ua_team_final_project.config;


import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import java.net.URI;

@Configuration
public class DigitalOceanConfig {

    private final Dotenv dotenv = Dotenv.load();

    @Bean
    public S3Client getS3Client() {
        String endpoint = dotenv.get("DO_ENDPOINT");
        String accessKey = dotenv.get("DO_ACCESS_KEY");
        String secretKey = dotenv.get("DO_SECRET_KEY");
        String region = dotenv.get("DO_REGION");

        System.out.println("DEBUG: Endpoint -> " + endpoint);
        System.out.println("DEBUG: Access Key -> " + accessKey);
        System.out.println("DEBUG: Secret Key -> " + (secretKey != null ? "Loaded" : "NULL"));
        System.out.println("DEBUG: Region -> " + region);

        if (endpoint == null || accessKey == null || secretKey == null || region == null) {
            throw new RuntimeException("Missing environment variables! Check application.properties or .env.");
        }
        return S3Client.builder()
                .endpointOverride(URI.create(endpoint))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKey, secretKey)))
                .serviceConfiguration(S3Configuration.builder().pathStyleAccessEnabled(true).build())
                .region(Region.of(region))
                .build();
    }
}
